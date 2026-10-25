
package com.pc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pc.dto.UserResponse;

@FeignClient(name = "USER-SERVICE")
public interface IUserClient {

    @GetMapping("/api/user/{id}")
    public UserResponse getUserById(@PathVariable("id") Long id);
}

