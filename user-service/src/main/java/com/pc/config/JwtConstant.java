package com.pc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class JwtConstant {
	
	@Value("${jwt.secret}")
	public String jwtToken;
}
