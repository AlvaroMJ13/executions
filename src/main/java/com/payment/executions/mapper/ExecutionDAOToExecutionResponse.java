package com.payment.executions.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.payment.executions.controller.response.ExecutionResponse;
import com.payment.executions.controller.response.Historic;
import com.payment.executions.controller.response.Status;
import com.payment.executions.dao.ExecutionDAO;

public class ExecutionDAOToExecutionResponse {
	
	public static ExecutionResponse convert(ExecutionDAO execution) {
		List<Historic> historicList = execution.getExecutionStatusDAO().stream().map(statusDAO -> {
			
			return Historic.builder().execution(Status.builder().status(statusDAO.getStatusDao().getName()).timestamp(statusDAO.getTimestamp()).build()).build();
			
		}).collect(Collectors.toList());
		
		
		ExecutionResponse executionResponse = ExecutionResponse.builder()
			.id(execution.getId())
			.entity(execution.getEntityDao().getName())
			.gtsMessageId(String.valueOf(execution.getGtsMessageId()))
			.globalOperationId(String.valueOf(execution.getGlobalOperationId()))
			.lastExecute(historicList.get(historicList.size() - 1).getExecution().getStatus())
			.historic(historicList).build();
		return executionResponse;
	}

}
