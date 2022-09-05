package com.payment.executions.unit;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.payment.executions.controller.request.EntityStatusRequest;
import com.payment.executions.dao.EntityDAO;
import com.payment.executions.dao.EntityStatusDAO;
import com.payment.executions.dao.StatusDAO;
import com.payment.executions.repository.EntityRepository;
import com.payment.executions.repository.EntityStatusRepository;
import com.payment.executions.repository.StatusRepository;
import com.payment.executions.service.StatusService;
import com.payment.executions.service.impl.StatusServiceImpl;

@SpringBootTest
class StatusEntityUnitTests {

	
	@Mock
	private EntityRepository entityRepository;
	
	@Mock
	private StatusRepository statusRepository;
	
	@Mock
	private EntityStatusRepository entityStatusRepository;
	
	@InjectMocks
	private StatusService statusService = new StatusServiceImpl();
	
	@Test
	void createStatus() {
		
		String status = "OTHER";
		when(statusRepository.save(any(StatusDAO.class))).thenReturn(StatusDAO.builder().id(1).build());
		
		int id = statusService.createStatus(status);
		
		assertEquals(1, id);

	}
	
	@Test
	void createEntity() {
		
		String entity = "OTHER";
		when(entityRepository.save(any(EntityDAO.class))).thenReturn(EntityDAO.builder().id(1).build());
		
		int id = statusService.createEntity(entity);
		
		assertEquals(1, id);

	}
	
	@Test
	void createEntityStatus() {
		
		when(entityStatusRepository.save(any(EntityStatusDAO.class))).thenReturn(EntityStatusDAO.builder().idEntity(1).idStatus(1).orderstep(1).rungateway(false).build());
		
		boolean result = statusService.createEntityStatus(EntityStatusRequest.builder().entityId(1).statusId(1).order(1).gateway(false).build());
		
		assertEquals(true, result);

	}
	

}
