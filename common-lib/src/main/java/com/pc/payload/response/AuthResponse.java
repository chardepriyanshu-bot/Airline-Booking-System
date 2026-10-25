package com.pc.payload.response;

import com.pc.dto.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	
	private String jwt;
	private String message;
	private String title;
	private UserResponse userResponse;
}
