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
	public String entity;
	
	public int entityId;
	
	public String idExecution;
	
	@NotNull
	public String gtsMessageId;
	
	public String operationGlobalId;
	
	public String operation;
	
	public int operationId;
	
}
