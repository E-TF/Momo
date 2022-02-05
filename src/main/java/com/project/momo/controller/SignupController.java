package com.project.momo.controller;

import com.project.momo.dto.signup.SignupForm;
import com.project.momo.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/signup")
public class SignupController {

    private final SignupService signupService;

    @PostMapping
    public void signup(@ModelAttribute @Valid SignupForm signupForm) {
        signupService.signup(signupForm);
    }
}
