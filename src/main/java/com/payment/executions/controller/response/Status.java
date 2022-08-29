package com.payment.executions.controller.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
	
	private String status;
    private LocalDateTime timestamp;
    private String gtsMessageId;

}
