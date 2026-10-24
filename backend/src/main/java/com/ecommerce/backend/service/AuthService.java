package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.LoginRequest;
import com.ecommerce.backend.dto.LoginResponse;
import com.ecommerce.backend.entity.Role;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private com.ecommerce.backend.security.JwtUtil jwtUtil;

	public LoginResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
		String roles = user.getRoles().stream()
			.map(Role::getName)
			.collect(Collectors.joining(","));
		String[] roleArray = roles.isEmpty() ? new String[0] : roles.split(",");

		String jwt = jwtUtil.generateToken(org.springframework.security.core.userdetails.User.withUsername(
			loginRequest.getUsername()).password("").roles(roleArray).build());

		return new LoginResponse(jwt, user.getId(), user.getUsername(), user.getEmail(), roles);
	}

	public User register(String username, String email, String rawPassword) {
		if (userRepository.existsByUsername(username)) {
			throw new RuntimeException("Username is already taken!");
		}
		if (userRepository.existsByEmail(email)) {
			throw new RuntimeException("Email is already in use!");
		}

		User user = new User(username, passwordEncoder.encode(rawPassword), email);
		user.getRoles().add(roleRepository.findByName("USER").orElseGet(() -> {
			Role defaultRole = new Role("USER");
			return roleRepository.save(defaultRole);
		}));
		return userRepository.save(user);
	}
}
