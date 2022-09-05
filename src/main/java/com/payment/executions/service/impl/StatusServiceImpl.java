package com.payment.executions.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.executions.controller.request.EntityStatusRequest;
import com.payment.executions.dao.EntityDAO;
import com.payment.executions.dao.EntityStatusDAO;
import com.payment.executions.dao.EntityStatusId;
import com.payment.executions.dao.StatusDAO;
import com.payment.executions.repository.EntityRepository;
import com.payment.executions.repository.EntityStatusRepository;
import com.payment.executions.repository.StatusRepository;
import com.payment.executions.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService{
	
	@Autowired
	private EntityRepository entityRepository;
	
	@Autowired
	private EntityStatusRepository entityStatusRepository;
	
	@Autowired
	private StatusRepository statusRepository;

	@Override
	public int createStatus(String name) {
		return statusRepository.save(StatusDAO.builder().name(name).build()).getId();
	}

	@Override
	public int createEntity(String name) {
		return entityRepository.save(EntityDAO.builder().name(name).build()).getId();
	}

	@Override
	public boolean createEntityStatus(EntityStatusRequest entityStatus) {
		EntityStatusDAO entityStatusDAO = EntityStatusDAO.builder()
				.idEntity(entityStatus.getEntityId())
				.idStatus(entityStatus.getStatusId())
				.orderstep(entityStatus.getOrder())
				.rungateway(entityStatus.isGateway())
				.build();
		entityStatusRepository.save(entityStatusDAO);
		return true;
		
	}

	@Override
	public void deleteStatus(int id) {
		statusRepository.deleteById(id);
		
	}

	@Override
	public void deleteEntity(int id) {
		entityRepository.deleteById(id);
		
	}

	@Override
	public void deleteEntityStatus(int entityId, int statusId) {
		entityStatusRepository.deleteById(EntityStatusId.builder().idEntity(entityId).idStatus(statusId).build());
		
	}

	
	

}
