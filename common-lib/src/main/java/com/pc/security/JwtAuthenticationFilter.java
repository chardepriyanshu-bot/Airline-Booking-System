
package com.pc.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenValidator jwtTokenValidator;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		// Step 1: Check JWT header
		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = header.substring(7);

		try {
			// Step 2: Validate token
			Claims claims = jwtTokenValidator.validateToken(token);

			// Step 3: Extract email
			String email = claims.get("email", String.class);

			if (email == null || email.isBlank()) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
				return;
			}

			// Step 4: Extract roles
			Object authoritiesClaim = claims.get("authorities");

			List<SimpleGrantedAuthority> authorities = extractAuthorities(authoritiesClaim);

			// Step 5: Create authentication
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null,
					authorities);

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			// Step 6: Store authentication
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (JwtException | IllegalArgumentException ex) {

			SecurityContextHolder.clearContext();

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
			return;
		}

		// Step 7: Continue request
		filterChain.doFilter(request, response);
	}

	private List<SimpleGrantedAuthority> extractAuthorities(Object claim) {

		if (claim == null) {
			return List.of();
		}

		if (claim instanceof String authorities) {
			return Arrays.stream(authorities.split(",")).map(String::trim).filter(role -> !role.isBlank())
					.map(SimpleGrantedAuthority::new).toList();
		}

		if (claim instanceof List<?> authorities) {
			return authorities.stream().filter(String.class::isInstance).map(String.class::cast)
					.map(SimpleGrantedAuthority::new).toList();
		}

		return List.of();
	}
}
