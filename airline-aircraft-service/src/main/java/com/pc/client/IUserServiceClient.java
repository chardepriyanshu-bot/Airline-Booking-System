package com.pc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pc.dto.UserResponse;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface IUserServiceClient {

	@GetMapping("/api/user/{userId}")
	public UserResponse getUserById(@PathVariable Long userId);
}
