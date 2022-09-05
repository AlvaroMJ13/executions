package com.payment.executions.unit;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.payment.executions.controller.request.ExecutionRequest;
import com.payment.executions.dao.EntityDAO;
import com.payment.executions.dao.EntityStatusDAO;
import com.payment.executions.dao.ExecutionDAO;
import com.payment.executions.dao.ExecutionStatusDAO;
import com.payment.executions.dao.StatusDAO;
import com.payment.executions.repository.EntityRepository;
import com.payment.executions.repository.EntityStatusRepository;
import com.payment.executions.repository.ExecutionRepository;
import com.payment.executions.repository.ExecutionStatusRepository;
import com.payment.executions.repository.StatusRepository;
import com.payment.executions.service.ExecutionService;
import com.payment.executions.service.impl.ExecutionServiceImpl;

@SpringBootTest
class ExecutionsUnitTests {

	@Mock
	private ExecutionRepository executionRepository;
	
	@Mock
	private ExecutionStatusRepository executionStatusRepository;
	
	@Mock
	private EntityRepository entityRepository;
	
	@Mock
	private StatusRepository statusRepository;
	
	@Mock
	private EntityStatusRepository entityStatusRepository;
	
	@InjectMocks
	private ExecutionService executionService = new ExecutionServiceImpl();
	
	@Test
	void createNewExecution() throws Exception {
		
		
		String entity = "ONE-APP";
		String gtsMessageId = "05091150";
		UUID uuid = UUID.fromString("86a31ea9-7d38-4aa9-b7f3-67f23aca6109");
		
		when(entityRepository.findByName(entity)).thenReturn(Optional.of(EntityDAO.builder().id(1).name(entity).build()));
		
		ExecutionDAO executionDAO = ExecutionDAO.builder().id(uuid).entityId(1).gtsMessageId(gtsMessageId).build();
		when(executionRepository.save(any(ExecutionDAO.class))).thenReturn(executionDAO);

		ExecutionRequest executionRequest = ExecutionRequest.builder().entity(entity).gtsMessageId(gtsMessageId).build();
		UUID result = executionService.createExecution(executionRequest, null);
		
		assertEquals(uuid, result);

	}
	
	@Test
	void getExecutionById() {
		
		
		String gtsMessageId = "05091150";
		UUID uuid = UUID.fromString("86a31ea9-7d38-4aa9-b7f3-67f23aca6109");
		
		ExecutionDAO executionDAO = ExecutionDAO.builder().id(uuid).entityId(1).gtsMessageId(gtsMessageId).build();
		when(executionRepository.findById(uuid)).thenReturn(Optional.of(executionDAO));

		ExecutionDAO result = executionService.getExecutionById(uuid).get();
		
		assertEquals(uuid, result.getId());

	}
	
	@Test
	void createExecution() {
		
		
		String gtsMessageId = "05091150";
		UUID uuid = UUID.fromString("86a31ea9-7d38-4aa9-b7f3-67f23aca6109");
		
		ExecutionDAO executionDAO = ExecutionDAO.builder().id(uuid).entityId(1).gtsMessageId(gtsMessageId).build();
		ExecutionStatusDAO executionStatusDAO = ExecutionStatusDAO.builder().id(uuid).build();
		
		when(executionRepository.save(any(ExecutionDAO.class))).thenReturn(executionDAO);
		when(executionStatusRepository.save(any(ExecutionStatusDAO.class))).thenReturn(executionStatusDAO);

		UUID result = executionService.createExecution(executionDAO);
		assertEquals(uuid, result);

	}
	
	@Test
	void createExecutionStatus() {
		
		
		UUID uuid = UUID.fromString("86a31ea9-7d38-4aa9-b7f3-67f23aca6109");
		
		ExecutionStatusDAO executionStatusDAO = ExecutionStatusDAO.builder().id(uuid).build();
		
		when(executionStatusRepository.save(any(ExecutionStatusDAO.class))).thenReturn(executionStatusDAO);

		boolean result = executionService.createExecutionStatus(executionStatusDAO);
		assertTrue(result);

	}
	
	@Test
	void getEntityIdFromName() {
		
		String entity = "ONE-APP";
		when(entityRepository.findByName(entity)).thenReturn(Optional.of(EntityDAO.builder().id(1).name(entity).build()));

		int result = executionService.getEntityIdFromName(entity);
		assertEquals(1, result);

	}
	
	@Test
	void getStatusIdFromName() {
		
		String status = "CREATED";
		when(statusRepository.findByName(status)).thenReturn(Optional.of(StatusDAO.builder().id(1).name(status).build()));

		int result = executionService.getStatusIdFromName(status);
		assertEquals(1, result);

	}
	
	@Test
	void getAllStatusByEntity() {
		
		EntityStatusDAO entityStatus1 = EntityStatusDAO.builder().idEntity(1).idStatus(1).orderstep(1).rungateway(false).build();
		EntityStatusDAO entityStatus2 = EntityStatusDAO.builder().idEntity(1).idStatus(2).orderstep(3).rungateway(true).build();
		
		when(entityStatusRepository.findByIdEntityOrderByOrderstepAsc(1)).thenReturn(List.of(entityStatus1, entityStatus2));

		List<EntityStatusDAO> result = executionService.getAllStatusByEntity(1);
		assertEquals(2, result.size());

	}

}
