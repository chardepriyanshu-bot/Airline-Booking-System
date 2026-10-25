package com.pc.service;

import com.pc.dto.UserRequest;
import com.pc.payload.response.AuthResponse;

public interface IAuthService {
	
	public AuthResponse login(String email, String password);
	public AuthResponse signup(UserRequest req);
}
