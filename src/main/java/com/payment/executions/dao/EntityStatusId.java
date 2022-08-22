package com.payment.executions.dao;

import java.io.Serializable;

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
public class EntityStatusId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idEntity;
	private int idStatus;

}
