package com.payment.executions.service;

import com.payment.executions.controller.request.EntityStatusRequest;

public interface StatusService {
	
	int createStatus (String name);
	
	int createEntity (String name);
	
	void createEntityStatus (EntityStatusRequest entityStatus);
	
	void deleteStatus (int id);
	
	void deleteEntity (int id);
	
	void deleteEntityStatus (int entityId, int statusId);
}
