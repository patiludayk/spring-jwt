package com.uday.spring.springjwt.controller;

import com.uday.spring.springjwt.util.JwtTokenUtil;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@Slf4j
public class TokenController {

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public TokenController(JwtTokenUtil jwtTokenUtil){
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/parse")
    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity parseToken(@RequestParam("token") String token){
        final Jwt parsedToken = jwtTokenUtil.parse(token);
        return new ResponseEntity<>(parsedToken, HttpStatus.OK);
    }
}
