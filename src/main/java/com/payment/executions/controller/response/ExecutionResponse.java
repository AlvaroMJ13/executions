package com.payment.executions.controller.response;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ExecutionResponse {

	public UUID id;
	public String entity;
	public String lastExecute;
	public String gtsMessageId;
	public String globalOperationId;
	public List<Historic> historic;
	
}
