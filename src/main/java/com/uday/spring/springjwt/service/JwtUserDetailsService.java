package com.uday.spring.springjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("inMemoryUser")
    InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //for db user return to jwt
        //1. db connection
        //2. get user from user_table where username = 'username'
        //3. create user using resultset
        //4. return user
        //INFO: just for demo we will have 1 user from memory and another from database.
        if (inMemoryUserDetailsManager.userExists(username))
            return inMemoryUserDetailsManager.loadUserByUsername(username);
        throw new UsernameNotFoundException("User not found with username: " + username);

        /*if ("username".equals(username)) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            //grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User("username", "password", grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }*/
    }
}
