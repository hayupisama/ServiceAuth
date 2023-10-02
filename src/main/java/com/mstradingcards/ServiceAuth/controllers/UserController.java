package com.mstradingcards.ServiceAuth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public UserDTO getUserById() {
		// TODO Get user_id from JWT
		return userService.getUserById(null);
	}

	
	@DeleteMapping("/deleteUser/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}

	@PostMapping("/createUser")
	public UserDTO createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDTO loginDTO) {
		return ResponseEntity.ok(userService.login(loginDTO)); 
	}
	
	@PutMapping("/updatePassword/{id}")
	public UserDTO updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
		// TODO Get user_id from JWT
		return userService.updatePassword(null, oldPassword, newPassword);
	}

	
	@GetMapping("/findByEmail")
	public UserDTO findByEmail(@RequestParam String email) {
		return userService.findByEmail(email);
	}
}
