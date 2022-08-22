package com.payment.executions.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.executions.dao.StatusDAO;

@Repository
public interface StatusRepository extends JpaRepository<StatusDAO, Integer> {
	
	Optional<StatusDAO> findByName(String name);

}
