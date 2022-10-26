package com.uday.spring.springjwt.config;

import com.uday.spring.springjwt.filter.RequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    private final AuthenticationConfiguration configuration;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private RequestFilter requestFilter;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain allowPermissibleContext(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/", "/**/api-docs", "/aboutme", "/register", "/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/parse").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //do not store user's info in session
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();

        http.headers().frameOptions().disable(); //INFO: for H2 console

        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
