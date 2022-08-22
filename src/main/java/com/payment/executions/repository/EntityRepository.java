package com.payment.executions.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.executions.dao.EntityDAO;

@Repository
public interface EntityRepository extends JpaRepository<EntityDAO, Integer> {
	
	Optional<EntityDAO> findByName(String name);

}
