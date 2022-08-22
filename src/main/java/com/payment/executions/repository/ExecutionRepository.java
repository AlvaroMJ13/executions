package com.payment.executions.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.executions.dao.ExecutionDAO;

@Repository
public interface ExecutionRepository extends JpaRepository<ExecutionDAO, UUID> {
	
	Optional<ExecutionDAO> findByGtsMessageId(Integer messasgeId);

}
