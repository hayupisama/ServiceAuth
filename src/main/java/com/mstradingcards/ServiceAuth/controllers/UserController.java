package com.mstradingcards.ServiceAuth.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mstradingcards.ServiceAuth.dto.AuthenticationResponse;
import com.mstradingcards.ServiceAuth.dto.LoginDTO;
import com.mstradingcards.ServiceAuth.dto.UserDTO;
import com.mstradingcards.ServiceAuth.models.User;
import com.mstradingcards.ServiceAuth.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/getAllUsers")
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public UserDTO getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@GetMapping("/")
	public UserDTO getUserById(Authentication authentication) {
		Long userId = userService.getUserIdFromUsername(authentication.getName());
		return userService.getUserById(userId);
	}

	@DeleteMapping("/deleteUser/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}

	@PostMapping("/createUser")
	public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
		UserDTO userDTO = userService.createUser(user);
		return Optional.ofNullable(userDTO).map(d -> {
			return ResponseEntity.ok(userDTO);
		}).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDTO loginDTO) {
		return ResponseEntity.ok(userService.login(loginDTO));
	}

	@PutMapping("/updatePassword/{id}")
	public UserDTO updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
			Authentication authentication) {
		Long userId = userService.getUserIdFromUsername(authentication.getName());
		return userService.updatePassword(userId, oldPassword, newPassword);
	}

	@GetMapping("/findByEmail")
	public UserDTO findByEmail(@RequestParam String email) {
		return userService.findByEmail(email);
	}
}
