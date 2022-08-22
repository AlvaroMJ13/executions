package com.payment.executions.controller.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class ExecutionRequest {

	@NotNull
	public String entity;
	
	public int entityId;
	
	@NotNull
	public String gtsMessageId;
	
	public String operationGlobalId;
	
	public String operation;
	
}
