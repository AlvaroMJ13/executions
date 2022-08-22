package com.payment.executions.dao;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@IdClass(ExecutionStatusId.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "executions_status")
public class ExecutionStatusDAO {
	
	@Id
	private UUID id;

	@Id
	@Column(name="statusid")
	private int statusId;
	
	@OneToOne
	@JoinColumn(name = "statusid", referencedColumnName = "id", insertable = false, updatable = false)
	private StatusDAO statusDao;

	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
	private ExecutionDAO executionDAO;

}
