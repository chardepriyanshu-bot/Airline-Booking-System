package com.pc.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pc.model.User;
import com.pc.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user=userRepository.findByEmail(email);
		
		if(user==null) throw new UsernameNotFoundException("User not found");
		
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
		
		Collection<GrantedAuthority> grantedAuthorities= Collections.singletonList(
				authority);
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), 
				user.getPassword(), grantedAuthorities);
	}

}
