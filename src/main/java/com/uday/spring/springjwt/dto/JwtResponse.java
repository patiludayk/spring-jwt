package com.uday.spring.springjwt.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -1L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

}