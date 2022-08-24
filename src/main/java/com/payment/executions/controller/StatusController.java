package com.payment.executions.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.executions.controller.request.EntityRequest;
import com.payment.executions.controller.request.EntityStatusRequest;
import com.payment.executions.controller.request.StatusRequest;
import com.payment.executions.service.StatusService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(StatusController.MANAGE_URL)
@Slf4j
public class StatusController {

	private static final String ENTITYSTATUS = "/entitystatus";

	private static final String ENTITY = "/entity";

	private static final String STATUS = "/status";

	static final String MANAGE_URL = "/manage";
	
	@Autowired
	private StatusService statusService;
	
	@PostMapping(path = STATUS)
	public ResponseEntity<Integer> createStatus(@Valid @RequestBody StatusRequest statusRequest ) {

		int idStored = 0;
		try {
			idStored = statusService.createStatus(statusRequest.getName());

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		log.info("Status created with Id {}", idStored);
		return ResponseEntity.ok().body(idStored);

	}
	
	@DeleteMapping(path = STATUS + "/{id}")
	public ResponseEntity<Integer> deleteStatus(@PathVariable int id) {

		try {
			statusService.deleteStatus(id);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		log.info("Status deleted with Id {}", id);
		return ResponseEntity.ok().build();

	}
	
	@PostMapping(path = ENTITY)
	public ResponseEntity<Integer> createEntity(@Valid @RequestBody EntityRequest entityRequest ) {

		int idStored = 0;
		try {
			idStored = statusService.createEntity(entityRequest.getName());

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		log.info("Entity created with Id {}", idStored);
		return ResponseEntity.ok().body(idStored);

	}
	
	@DeleteMapping(path = ENTITY + "/{id}")
	public ResponseEntity<Integer> deleteEntity(@PathVariable int id) {

		try {
			statusService.deleteEntity(id);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		log.info("Entity deleted with Id {}", id);
		return ResponseEntity.ok().build();

	}
	
	@PostMapping(path = ENTITYSTATUS)
	public ResponseEntity<Integer> createEntityStatus(@Valid @RequestBody EntityStatusRequest entityStatusRequest ) {

		try {
			statusService.createEntityStatus(entityStatusRequest);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		log.info("EntityStatus created");
		return ResponseEntity.ok().build();

	}
	
	@DeleteMapping(path = ENTITYSTATUS + "/{entityId}/{statusId}")
	public ResponseEntity<Integer> deleteEntityStatus(@PathVariable int entityId, @PathVariable int statusId) {

		try {
			statusService.deleteEntityStatus(entityId, statusId);

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		log.info("Entity Status deleted");
		return ResponseEntity.ok().build();

	}
	
}
