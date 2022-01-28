package com.project.momo.controller;

import com.project.momo.dto.SignupForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/api/signup")
    public SignupForm signup() {
        return new SignupForm();
    }
}
