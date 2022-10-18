package com.uday.spring.springjwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor	////need default constructor for JSON Parsing
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = -1L;
	
	private String username;
	private String password;

	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

}