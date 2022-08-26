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
	@Column(name="entity_id")
	private int idEntity;

	@Id
	@Column(name="status_id")
	private int idStatus;
	
	@Column(name="order_step")
	private int orderstep;
	
	@Column(name="run_gateway")
	private boolean rungateway;

}
