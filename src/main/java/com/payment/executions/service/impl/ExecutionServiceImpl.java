package com.payment.executions.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.executions.client.GatewayClient;
import com.payment.executions.controller.request.ExecutionRequest;
import com.payment.executions.dao.EntityDAO;
import com.payment.executions.dao.EntityStatusDAO;
import com.payment.executions.dao.ExecutionDAO;
import com.payment.executions.dao.ExecutionStatusDAO;
import com.payment.executions.dao.StatusDAO;
import com.payment.executions.exception.OperationNotAllowed;
import com.payment.executions.exception.OperationNotFound;
import com.payment.executions.exception.OperationNotPresent;
import com.payment.executions.mapper.ExecutionRequestToExecutionDAO;
import com.payment.executions.repository.EntityRepository;
import com.payment.executions.repository.EntityStatusRepository;
import com.payment.executions.repository.ExecutionRepository;
import com.payment.executions.repository.ExecutionStatusRepository;
import com.payment.executions.repository.StatusRepository;
import com.payment.executions.service.ExecutionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExecutionServiceImpl implements ExecutionService{
	
	@Autowired
	private ExecutionRepository executionRepository;
	
	@Autowired
	private ExecutionStatusRepository executionStatusRepository;
	
	@Autowired
	private EntityRepository entityRepository;
	
	@Autowired
	private EntityStatusRepository entityStatusRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private GatewayClient gatewayClient;
	
	
	@Override
	public UUID createExecution(ExecutionRequest executionRequest) throws OperationNotFound, OperationNotAllowed, OperationNotPresent  {
		
		Optional<ExecutionDAO> executionStoredOptional = getExecutionByMessageId(executionRequest.getGtsMessageId());
		if (executionStoredOptional.isEmpty()) {
			int entityId = getEntityIdFromName(executionRequest.getEntity());
			executionRequest.setEntityId(entityId);
			ExecutionDAO executionDao = ExecutionRequestToExecutionDAO.convert(executionRequest);
			executionDao.setStatusId(1);
			return createExecution(executionDao);
			
		} else {
			
			if (executionRequest.getOperation() == null && executionRequest.getOperationId() == 0) {
				log.error("No existe el parámetro operation en la peticion");
				throw new OperationNotPresent("No existe el parámetro operation en la peticion");
			}
			
			ExecutionDAO executionStored = executionStoredOptional.get();
			manageStateMachine(executionRequest, executionStored);
			
			return executionStored.getId();
			
		}
	}

	private void manageStateMachine(ExecutionRequest executionRequest, ExecutionDAO executionStored)
			throws OperationNotFound, OperationNotAllowed {
		int lastStatus = executionStored.getExecutionStatusDAO().get(executionStored.getExecutionStatusDAO().size() - 1).getStatusId();
		
		List<EntityStatusDAO> entityStatusList = getAllStatusByEntity(executionStored.getEntityId());
		
		int lastOrderStatus = entityStatusList.stream().filter(entityStatus -> entityStatus.getIdStatus() == lastStatus).findFirst().get().getOrderstep();
		int nextStatusIdFromRequest;
		if (executionRequest.getOperationId() != 0) {
			nextStatusIdFromRequest = executionRequest.getOperationId();
		} else {
			nextStatusIdFromRequest = getStatusIdFromName(executionRequest.getOperation());
		}
		
		EntityStatusDAO nextEntityStatus = entityStatusList.stream().filter(entityStatus -> entityStatus.getIdStatus() == nextStatusIdFromRequest).findFirst().orElseThrow(() -> new OperationNotFound());
		
		
		log.info("Se ha encontrado el id {} para la operacion solicitada {}", nextEntityStatus.getIdStatus(), executionRequest.getOperation());
		if (validOrderStep(lastOrderStatus, nextEntityStatus)) {
		
			createExecutionStatus(ExecutionStatusDAO.builder().id(executionStored.getId()).statusId(nextEntityStatus.getIdStatus()).timestamp(LocalDateTime.now()).build());
			
			if (nextEntityStatus.isRungateway()) {
				//Call Gateway next step
				try {
					ResponseEntity<String> response = gatewayClient.getStep1();
					log.info("Codigo respuesta Gateway: {}", response.getStatusCode());
					
					if (response.getStatusCode() == HttpStatus.OK ) {
						int nextStatusId = entityStatusList.stream().filter(entityStatus -> entityStatus.getOrderstep() == nextEntityStatus.getOrderstep() + 1).findFirst().orElseThrow(() -> new OperationNotFound()).getIdStatus();
						createExecution(ExecutionRequest.builder().operationId(nextStatusId).gtsMessageId(executionRequest.getGtsMessageId()).build());
					}
				} catch (Exception e) {
					log.error("Ha habido un error al conectar con el Gateway: {}", e.getLocalizedMessage());
				}
			}
			
		} else {
			log.error("La operacion solicitada {} no coincide con el siguiente estado", executionRequest.getOperation());
			throw new OperationNotAllowed(String.format("La operacion solicitada %s no coincide con el siguiente estado", executionRequest.getOperation()));
		}
	}

	private boolean validOrderStep(int lastOrderStatus, EntityStatusDAO nextEntityStatus) {
		return lastOrderStatus == nextEntityStatus.getOrderstep() || lastOrderStatus + 1 == nextEntityStatus.getOrderstep() || lastOrderStatus - 1 == nextEntityStatus.getOrderstep();
	}
	
	@Override
	public Optional<ExecutionDAO> getExecutionById(UUID id) {
		return executionRepository.findById(id);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public UUID createExecution(ExecutionDAO executionDAO) {
		UUID id = executionRepository.save(executionDAO).getId();
		createExecutionStatus(ExecutionStatusDAO.builder().id(id).statusId(executionDAO.getStatusId()).timestamp(LocalDateTime.now()).build());
		log.info("Ejecución creada correctamente con id: {}", id);
		return id;
	}

	@Override
	public Optional<ExecutionDAO> getExecutionByMessageId(String messasgeId) {
		return executionRepository.findByGtsMessageId(messasgeId);
	}

	@Override
	public boolean createExecutionStatus(ExecutionStatusDAO executionStatusDAO) {
		executionStatusRepository.save(executionStatusDAO);
		log.info("ExecutionStatus creada correctamente para el id: {}", executionStatusDAO.getId());
		return true;
	}

	@Override
	public int getEntityIdFromName(String name) {
		Optional<EntityDAO> entityDAO = entityRepository.findByName(name);
		return entityDAO.orElseThrow().getId();
	}

	@Override
	public List<EntityStatusDAO> getAllStatusByEntity(int entityId) {
		return entityStatusRepository.findByIdEntityOrderByOrderstepAsc(entityId);
	}

	@Override
	public int getStatusIdFromName(String name) {
		Optional<StatusDAO> statusDAO = statusRepository.findByName(name);
		return statusDAO.orElseThrow().getId();
	}

}
