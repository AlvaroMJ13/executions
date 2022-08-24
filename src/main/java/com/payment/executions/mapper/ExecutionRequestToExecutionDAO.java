package com.payment.executions.mapper;

import com.payment.executions.controller.request.ExecutionRequest;
import com.payment.executions.dao.ExecutionDAO;

public class ExecutionRequestToExecutionDAO {
	

	public static ExecutionDAO convert(ExecutionRequest executionRequest) {
		
		ExecutionDAO executionDao = ExecutionDAO.builder()
					.entityId(executionRequest.getEntityId())
					.gtsMessageId(executionRequest.getGtsMessageId())
				.build();
		return executionDao;
	}

}
