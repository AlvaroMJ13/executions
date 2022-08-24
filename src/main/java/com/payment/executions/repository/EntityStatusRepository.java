package com.payment.executions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.executions.dao.EntityStatusDAO;
import com.payment.executions.dao.EntityStatusId;

@Repository
public interface EntityStatusRepository extends JpaRepository<EntityStatusDAO, EntityStatusId> {
	
	List<EntityStatusDAO> findByIdEntityOrderByOrderstepAsc (int entityId);
	
}
