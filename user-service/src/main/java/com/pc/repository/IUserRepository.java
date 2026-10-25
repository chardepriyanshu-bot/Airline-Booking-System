package com.pc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pc.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);
	
}
