package com.pc.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationRequest {
	
	@NotBlank(message = "Sender mail is required.")
    private String to;
	
	@NotBlank(message = "Subject is required.")
    private String subject;
	
	@NotBlank(message = "Subject is required.")
    private String message;
}
