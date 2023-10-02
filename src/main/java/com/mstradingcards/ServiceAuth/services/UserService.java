package com.mstradingcards.ServiceAuth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mstradingcards.ServiceAuth.config.JwtService;
import com.mstradingcards.ServiceAuth.dto.AuthenticationResponse;
import com.mstradingcards.ServiceAuth.dto.LoginDTO;
import com.mstradingcards.ServiceAuth.dto.PlayerDTO;
import com.mstradingcards.ServiceAuth.dto.UserDTO;
import com.mstradingcards.ServiceAuth.models.User;
import com.mstradingcards.ServiceAuth.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private WebClient webClient;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;	

	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id).orElse(null);
		return (user != null) ? convertToDTO(user) : null;
	}

	public UserDTO findByEmail(String email) {
		User user = userRepository.findByEmail(email).orElse(null);
		return (user != null) ? convertToDTO(user) : null;
	}

	public UserDTO createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User save = userRepository.save(user);

		/*
		 * PlayerDTO playerDTO = convertToPlayerDTO(save);
		 * webClient.post().uri("http://localhost:8081/api/players/createPlayer").body(
		 * BodyInserters.fromValue(playerDTO))
		 * .retrieve().bodyToMono(PlayerDTO.class).block();
		 */
		return convertToDTO(save);
	}

	public UserDTO updatePassword(Long id, String oldPassword, String newPassword) {
		User existingUser = userRepository.findById(id).orElse(null);

		if (existingUser != null) {
			if (passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
				existingUser.setPassword(passwordEncoder.encode(newPassword));
				User updatedUser = userRepository.save(existingUser);
				return convertToDTO(updatedUser);
			} else {
				throw new IllegalArgumentException("Old password is incorrect.");
			}
		}

		return null; // User not found
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public AuthenticationResponse login(LoginDTO request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String jwtToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		
		return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	private UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(user.getUsername());
		userDTO.setEmail(user.getEmail());
		userDTO.setRole(user.getRole());
		return userDTO;
	}

	private PlayerDTO convertToPlayerDTO(User user) {
		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setUsername(user.getUsername());
		playerDTO.setEmail(user.getEmail());
		playerDTO.setUser_id(user.getId());
		return playerDTO;
	}

}
