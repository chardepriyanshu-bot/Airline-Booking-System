package com.pc.dto;

import com.pc.enums.User_Role;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	@NotBlank
	@Column(nullable = false)
	private String fullName;
	
	@NotBlank
	@Column(nullable = false)
	private String email;
	
	@NotBlank
	@Column(nullable = false)
	private String password;
	
	@NotBlank
	@Column(nullable = false)
	private String phoneNumber;
	
	@Valid
	private User_Role role;
}
