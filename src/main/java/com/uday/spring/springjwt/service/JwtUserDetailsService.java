package com.uday.spring.springjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("inMemoryUser")
    InMemoryUserDetailsManager inMemoryUserDetailsManager;

    private static final String DB_USERS = "db_.*";
    private static final String IM_USERS = "im_.*";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //for db user return to jwt
        //1. db connection
        //2. get user from user_table where username = 'username'
        //3. create user using resultset
        //4. return user
        //INFO: just for demo we will have 1 user from memory and another from database.
        if (isDbUser(username)) {
            //TODO: return from database
        } else if (inMemoryUserDetailsManager.userExists(username)) {
            return inMemoryUserDetailsManager.loadUserByUsername(username);
        }
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

    private boolean isDbUser(String username) {
        Pattern dbUserPattern = Pattern.compile(DB_USERS);
        Pattern imUserPattern = Pattern.compile(IM_USERS);
        if (dbUserPattern.matcher(username).matches()) {
            return true;
        } else if (imUserPattern.matcher(username).matches()) {
            return false;
        } else {
            throw new UsernameNotFoundException("invalid username pattern. expected pattern db_.* or im_.*");
        }
    }
}
