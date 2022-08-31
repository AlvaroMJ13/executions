package com.payment.executions.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
import com.payment.executions.kafka.KafkaProducerService;
import com.payment.executions.kafka.model.RegisterMessage;
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
	
	@Autowired
	private KafkaProducerService kafkaService;
	
	
	@Override
	public UUID createExecution(ExecutionRequest executionRequest, ExecutionDAO executionDAO) throws OperationNotFound, OperationNotAllowed  {
		
		if (executionDAO == null) {
			int entityId = getEntityIdFromName(executionRequest.getEntity());
			executionRequest.setEntityId(entityId);
			ExecutionDAO executionDao = ExecutionRequestToExecutionDAO.convert(executionRequest);
			executionDao.setStatusId(1);
			executionDao.setGtsMessageId(executionRequest.getGtsMessageId());
			return createExecution(executionDao);
			
		} else {
			
			int lastStatus = executionDAO.getExecutionStatusDAO().get(executionDAO.getExecutionStatusDAO().size() - 1).getStatusId();
			List<EntityStatusDAO> entityStatusList = getAllStatusByEntity(executionDAO.getEntityId());
			if (lastStatus != 3 || executionRequest.getIdExecution() != null) {
				manageNextState(entityStatusList, executionDAO.getId(), lastStatus, executionRequest.getGtsMessageId());
			}
	
			return executionDAO.getId();
			
		}
	}

	private void manageNextState(List<EntityStatusDAO> entityStatusList, UUID id, int lastStatus, String gtsMessageId)
			throws OperationNotFound, OperationNotAllowed {
		
		int lastOrderStatus = entityStatusList.stream().filter(entityStatus -> entityStatus.getIdStatus() == lastStatus).findFirst().get().getOrderstep();
		
		EntityStatusDAO nextEntityStatus;
		
		if (lastOrderStatus + 1 > entityStatusList.size()) {
			throw new OperationNotFound("Execution in last status");
		}
		
		nextEntityStatus = entityStatusList.stream()
				.filter(entityStatus -> entityStatus.getOrderstep() == lastOrderStatus + 1)
				.findFirst().orElseThrow(() -> new OperationNotFound("Status not found"));
		
		
		log.info("Se ha encontrado el id {} para la operacion solicitada ", nextEntityStatus.getIdStatus());
		if (validOrderStep(lastOrderStatus, nextEntityStatus)) {
		
			createExecutionStatus(ExecutionStatusDAO.builder()
					.id(id)
					.statusId(nextEntityStatus.getIdStatus())
					.timestamp(LocalDateTime.now())
					.gtsMessageId(gtsMessageId)
					.build());
			
			kafkaService.sendMessage(RegisterMessage.builder()
					.idExecution(id.toString())
					.gtsMessageId(gtsMessageId)
					.status(statusRepository.findById(nextEntityStatus.getIdStatus()).get().getName())
					.build());
			
			if (nextEntityStatus.isRungateway()) {
				//Call Gateway next step
				try {
					
					if (sendRequestToGateway(gtsMessageId, id.toString()) == HttpStatus.OK ) {
						manageNextState(entityStatusList, id, nextEntityStatus.getIdStatus(), gtsMessageId);
					}
				} catch (Exception e) {
					log.error("Ha habido un error al conectar con el Gateway: {}", e.getLocalizedMessage());
				}
			}
			
		} else {
			log.error("La operacion solicitada no coincide con el siguiente estado");
			throw new OperationNotAllowed("La operacion solicitada no coincide con el siguiente estado");
		}
	}

	private HttpStatus sendRequestToGateway(String gtsMessageId, String idExecution) {
		ResponseEntity<String> response = gatewayClient.getStep1(gtsMessageId, idExecution);
		log.info("Codigo respuesta Gateway: {}", response.getStatusCode());
		return response.getStatusCode();
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
		createExecutionStatus(ExecutionStatusDAO.builder()
				.id(id)
				.statusId(executionDAO.getStatusId())
				.timestamp(LocalDateTime.now())
				.gtsMessageId(executionDAO.getGtsMessageId())
				.build());
		log.info("Ejecución creada correctamente con id: {}", id);
		return id;
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

	@Override
	@Async
	public void createSecondStepStatus(int entityId, UUID id, String gtsMessageId) {
		try {
			
			List<EntityStatusDAO> entityStatusList = getAllStatusByEntity(entityId);
			manageNextState(entityStatusList, id, 1, gtsMessageId);
		} catch (OperationNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationNotAllowed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	@Override
	public Optional<ExecutionDAO> getExecutionByGtsMessageId(String gtsMessageId) {
		return executionRepository.findByExecutionStatusDAOGtsMessageId(gtsMessageId);
	}

}
