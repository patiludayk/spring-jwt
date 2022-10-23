package com.uday.spring.springjwt.controller;

import com.uday.spring.springjwt.dto.JwtRequest;
import com.uday.spring.springjwt.dto.JwtResponse;
import com.uday.spring.springjwt.service.JwtUserDetailsService;
import com.uday.spring.springjwt.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationController(JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        authenticateRequest(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private Authentication authenticate(Authentication auth) throws AuthenticationException {
        if (auth.getName().equals(auth.getPrincipal())) {
            return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials());
        }
        throw new BadCredentialsException("Bad Credentials");
    }

    private void authenticateRequest(String username, String password) throws Exception {
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(username, password);
            Authentication result = authenticate(request);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (StackOverflowError e) {
            log.error("error: {}", e);
        } catch (Exception e) {
            throw e;
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (StackOverflowError e) {
            log.error("error: {}", e);
        } catch (Exception e) {
            throw e;
        }
    }
}
