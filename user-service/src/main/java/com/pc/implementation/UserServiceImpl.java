package com.pc.implementation;

import java.util.List;

import org.springframework.stereotype.Service;
import com.pc.dto.UserResponse;
import com.pc.exception.UserNotFoundException;
import com.pc.mapper.UserMapper;
import com.pc.model.User;
import com.pc.repository.IUserRepository;
import com.pc.service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
	
	private final IUserRepository userRepository;
	
	@Override
	public UserResponse getUserbyEmail(String email) {
		
		User user=userRepository.findByEmail(email);
		if(user==null)
			throw new UserNotFoundException("User not found");
		
		return UserMapper.toResponse(user);
	}

	@Override
	public UserResponse getUserById(Long id) {
		User user=userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("User not found"));
		
		return UserMapper.toResponse(user);
	}

	@Override
	public List<UserResponse> getAllUser() {	
		
		return userRepository.findAll().stream().map(UserMapper::toResponse).toList();
	}

}
