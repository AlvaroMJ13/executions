package com.payment.executions.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.payment.executions.controller.request.ExecutionRequest;
import com.payment.executions.dao.EntityStatusDAO;
import com.payment.executions.dao.ExecutionDAO;
import com.payment.executions.dao.ExecutionStatusDAO;

public interface ExecutionService {
	
	Optional<ExecutionDAO> getExecutionById (UUID id);
	
	UUID createExecution (ExecutionRequest executionRequest) throws Exception;
	
	UUID createExecution (ExecutionDAO executionDAO);
	
	Optional<ExecutionDAO> getExecutionByMessageId (String messasgeId);
	
	boolean createExecutionStatus (ExecutionStatusDAO executionDAO);
	
	int getEntityIdFromName(String name);
	
	int getStatusIdFromName(String name);
	
	List<EntityStatusDAO> getAllStatusByEntity (int entityId);

}
