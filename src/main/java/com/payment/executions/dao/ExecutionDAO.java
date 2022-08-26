package com.payment.executions.dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "executions")
public class ExecutionDAO {
	
	@Id
	@Type(type="org.hibernate.type.PostgresUUIDType")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	@Column(name="entity_id")
	private int entityId;
	
	@OneToOne
	@JoinColumn(name = "entity_id", referencedColumnName = "id", insertable = false, updatable = false)
	private EntityDAO entityDao;

	@Column(name="gts_message_id")
	private String gtsMessageId;
	
	@Column(name="global_operation_id")
	private String globalOperationId;

	@OneToMany(mappedBy = "executionDAO", fetch = FetchType.EAGER)
	private List<ExecutionStatusDAO> executionStatusDAO;
	
	@Transient
	private int statusId;
}
