package com.payment.executions.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.executions.dao.ExecutionStatusDAO;

@Repository
public interface ExecutionStatusRepository extends JpaRepository<ExecutionStatusDAO, UUID> {
	
}
