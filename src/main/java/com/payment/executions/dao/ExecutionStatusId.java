package com.payment.executions.dao;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class ExecutionStatusId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID id;

	private int statusId;

}
