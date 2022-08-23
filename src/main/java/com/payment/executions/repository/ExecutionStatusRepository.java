package com.payment.executions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.executions.dao.ExecutionStatusDAO;
import com.payment.executions.dao.ExecutionStatusId;

@Repository
public interface ExecutionStatusRepository extends JpaRepository<ExecutionStatusDAO, ExecutionStatusId> {
	
}
