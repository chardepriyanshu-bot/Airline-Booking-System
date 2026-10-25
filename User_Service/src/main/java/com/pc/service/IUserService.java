package com.pc.service;

import java.util.List;

import com.pc.dto.UserResponse;


public interface IUserService {
	public UserResponse getUserbyEmail(String email);
	public UserResponse getUserById(Long id);
	public List<UserResponse> getAllUser();
}
