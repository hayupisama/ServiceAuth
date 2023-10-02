package com.mstradingcards.ServiceAuth.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PlayerDTO {

	@NotBlank(message = "Username is required")
	private String username;

	@NotNull
	private Long user_id;
	@Email(message = "Please provide a valid email address")
	@NotBlank(message = "Email is required")
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public Long getUser_id() {
		return user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
