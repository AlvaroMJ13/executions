package com.payment.executions.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@IdClass(EntityStatusId.class)
@Table(name = "entity_status")
public class EntityStatusDAO {
	
	@Id
	@Column(name="identity")
	private int idEntity;

	@Id
	@Column(name="idstatus")
	private int idStatus;
	
	private int order;
	
	private boolean rungateway;

}
