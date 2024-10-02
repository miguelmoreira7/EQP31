package com.eqp3e1.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private JdbcUserDetailsManager userDetailsManager;

    public void save(UserDetails user) {
        if (!userDetailsManager.userExists(user.getUsername())) {
            userDetailsManager.createUser(user);
        }
    }
}