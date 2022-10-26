package com.uday.spring.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class UserManagmentConfig {

    /*@Bean
    public UserDetailsService userDetailsService(@Qualifier BCryptPasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(bCryptPasswordEncoder.encode("userPass"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("adminPass"))
                .roles("USER", "ADMIN")
                .build());
        return manager;
    }*/

    //INFO: InMemory user creation.
    @Bean
    public InMemoryUserDetailsManager inMemoryUser() {
        UserDetails user = User
                .withUsername("db_user")   //warning: do not use withDefaultPasswordEncoder() in production
                .password("{bcrypt}password")
                .authorities("ADMIN")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    //INFO: this is used in case of database user - ie if we are getting/storing user and credentials in database.
    //INFO: user will be created in database H2
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
            .build();
        /*
        //INFO: DEFAULT_USER_SCHEMA_DDL_LOCATION script
        create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
        create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
        create unique index ix_auth_username on authorities (username,authority);
        */
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        UserDetails user = User
            .withUsername("im_user")
            .password("{bcrypt}password")
            .roles("USER")
            .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);
        return users;
    }

}
