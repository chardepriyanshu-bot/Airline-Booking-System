package com.pc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pc.dto.UserRequest;
import com.pc.payload.request.LoginRequest;
import com.pc.payload.response.AuthResponse;
import com.pc.service.IAuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
	
	private final IAuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signup(@Valid @RequestBody UserRequest request){
		AuthResponse authResponse = authService.signup(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest){
		AuthResponse authResponse = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
		return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
	}
}
