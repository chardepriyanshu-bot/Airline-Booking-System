
package com.pc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pc.payload.request.EmailNotificationRequest;
import com.pc.service.INotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Validated
public class NotificationController {

	private final INotificationService emailService;

	@PostMapping("/email")
	public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailNotificationRequest request) {

		emailService.sendEmail(request);

		return ResponseEntity.ok("Email notification sent successfully");
	}
}
