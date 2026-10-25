
package com.pc.implementation;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.pc.config.JwtConstant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthJwtProvider {

	private final SecretKey key;

	public AuthJwtProvider(JwtConstant jwtConstant) {
		this.key = Keys.hmacShaKeyFor(jwtConstant.getJwtToken()
				.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}

	public String generateToken(Authentication auth, Long userId) {

		String email = auth.getName();

		String authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.reduce((first, second) -> first + "," + second).orElse("");

		Date now = new Date();
		Date expiry = new Date(now.getTime() + 24 * 60 * 60 * 1000L);

		return Jwts.builder().claim("email", email).claim("authorities", authorities).claim("userId", userId)
				.issuedAt(now).expiration(expiry).signWith(key).compact();
	}
}
