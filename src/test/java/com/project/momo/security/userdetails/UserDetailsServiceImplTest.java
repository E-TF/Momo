package com.project.momo.security.userdetails;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsService userDetailsService;

    @Test
    void userDetailsServiceTest() {

        UserDetails userDetails = userDetailsService.loadUserByUsername("123");
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getPassword());
    }

}