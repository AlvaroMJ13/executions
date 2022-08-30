package com.payment.executions.controller.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Builder
public class ExecutionRequest {

	@NotNull
	private String entity;
	
	private int entityId;
	
	private String idExecution;
	
	@NotNull
	private String gtsMessageId;
	
	private String operationGlobalId;
	
	private String operation;
	
	private int operationId;
	
}
