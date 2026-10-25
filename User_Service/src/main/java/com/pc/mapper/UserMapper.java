package com.pc.mapper;

import java.util.List;

import com.pc.dto.UserResponse;
import com.pc.model.User;

public class UserMapper {
	
	public static UserResponse toResponse(User user) {
		if(user==null) return null;
		
		return UserResponse.builder()
				.id(user.getId())
				.fullName(user.getFullName())
				.email(user.getEmail())
				.role(user.getRole())
				.phoneNumber(user.getPhoneNumber())
				.lastLogin(user.getLastLogin())
				.build();
	}
	
	public static List<UserResponse> toDTOList(List<User> userList){
		if(userList==null) return null;
		
		return userList.stream().map(UserMapper::toResponse).toList();
	}
}
