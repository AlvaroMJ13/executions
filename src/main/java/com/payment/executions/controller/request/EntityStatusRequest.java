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
public class EntityStatusRequest {

	@NotNull
	public int entityId;
	
	@NotNull
	public int statusId;
	
	@NotNull
	public int order;
	
	@NotNull
	public boolean gateway;
	
}
