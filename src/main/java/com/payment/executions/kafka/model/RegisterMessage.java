package com.payment.executions.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterMessage {

	public String gtsMessageId;
	
	public String idExecution;
	
	public String status;
	
}
