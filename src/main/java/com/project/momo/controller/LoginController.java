package com.project.momo.controller;

import com.project.momo.security.userdetails.UserDetailsImpl;
import com.project.momo.security.userdetails.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {


    @PostMapping("/login")
    public void login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(principal.getUsername());
        System.out.println(principal.getPassword());
        System.out.println(principal.getAuthorities());
    }

}
