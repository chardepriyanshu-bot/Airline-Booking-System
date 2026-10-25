package com.pc.dto;

import java.time.LocalDateTime;

import com.pc.enums.User_Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private Long id;
	private String fullName;
	private String email;
	private String phoneNumber;
	private User_Role role;
	private LocalDateTime lastLogin;
	
}
