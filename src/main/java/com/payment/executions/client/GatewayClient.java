package com.payment.executions.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="gateway", url = "http://localhost:8081")
public interface GatewayClient {

	@GetMapping(value = "/step/status")
    ResponseEntity<String> getStep1(@RequestParam String gtsMessageId, @RequestParam String idExecution);
}
 