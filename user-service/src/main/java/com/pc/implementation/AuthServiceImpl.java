package com.pc.implementation;

import java.time.LocalDateTime;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pc.client.INotificationClient;
import com.pc.dto.UserRequest;
import com.pc.enums.User_Role;
import com.pc.exception.InvalidUserRoleException;
import com.pc.exception.UserNotFoundException;
import com.pc.mapper.UserMapper;
import com.pc.model.User;
import com.pc.payload.request.EmailNotificationRequest;
import com.pc.payload.response.AuthResponse;
import com.pc.repository.IUserRepository;
import com.pc.service.CustomUserDetailsService;
import com.pc.service.IAuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final com.pc.implementation.AuthJwtProvider provider;
	private final CustomUserDetailsService customUserDetailsService;
	private final INotificationClient notificationClient;

	@Override
	public AuthResponse login(String email, String password) {
		Authentication authenticate = authenticate(email, password);

		User user = userRepository.findByEmail(email);
		user.setLastLogin(LocalDateTime.now());

		User savedUser = userRepository.save(user);

		String jwt = provider.generateToken(authenticate, user.getId());
		
		sendLoginEmail(savedUser);

		return AuthResponse.builder().jwt(jwt).userResponse(UserMapper.toResponse(savedUser))
				.title("Welcome back " + savedUser.getFullName()).message("Login Successfully!").build();
	}

	@Override
	public AuthResponse signup(UserRequest req) {

		User existingUser = userRepository.findByEmail(req.getEmail());

		if (existingUser != null) {
			throw new UserNotFoundException("Email is already exist");
		}

		if (req.getRole() == User_Role.ADMIN) {
			throw new InvalidUserRoleException("You cannot sign up admin");
		}

		User user = User.builder().email(req.getEmail()).phoneNumber(req.getPhoneNumber())
				.password(passwordEncoder.encode(req.getPassword())).role(req.getRole()).fullName(req.getFullName())
				.lastLogin(LocalDateTime.now()).createdAt(LocalDateTime.now()).updateAt(LocalDateTime.now()).build();

		User savedUser = userRepository.save(user);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());

		String jwt = provider.generateToken(authentication, savedUser.getId());

		sendWelcomeEmail(savedUser);

		return AuthResponse.builder().jwt(jwt).userResponse(UserMapper.toResponse(savedUser))
				.title("Welcome " + savedUser.getFullName()).message("User Registration is successfully!").build();
	}

	private Authentication authenticate(String email, String password) {

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
		if (!passwordEncoder.matches(password, userDetails.getPassword()))
			throw new UsernameNotFoundException("Invalid password");

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	private void sendWelcomeEmail(User user) {

		EmailNotificationRequest request = EmailNotificationRequest.builder().to(user.getEmail())
				.subject("Welcome to Airline Booking System!")
				.message("Hi " + user.getFullName() + ",\n\n" + "Your account has been created successfully.\n"
						+ "Welcome to our Airline Booking System!\n\n" + "Happy journey!")
				.build();

		try {
			notificationClient.sendEmail(request);
		} catch (Exception e) {
			System.err.println("Welcome email failed: " + e.getMessage());
		}
	}

	private void sendLoginEmail(User user) {

		EmailNotificationRequest request = EmailNotificationRequest.builder().to(user.getEmail())
				.subject("New Login - Airline Booking System")
				.message("Hi " + user.getFullName() + ",\n\n" + "You have successfully logged in to your account.\n\n"
						+"Regards,\nAirline Booking Team")
				.build();

		try {
			notificationClient.sendEmail(request);
		} catch (Exception e) {
			System.err.println("Login notification email failed: " + e.getMessage());
		}
	}

}
