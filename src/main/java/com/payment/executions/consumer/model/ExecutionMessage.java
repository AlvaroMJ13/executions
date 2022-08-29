package com.payment.executions.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionMessage {

	public String gtsMessageId;
	
	public String operation;
	
	public String idExecution;
	
}
