package com.project.momo.controller;

import com.project.momo.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/signup")
public class SignupForm {

    private final SignupService signupService;

    @PostMapping
    public void signup(@ModelAttribute @Valid SignupForm signupForm) {
        signupService.signup(signupForm);
    }

    @PostMapping("/checkId")
    public ResponseEntity<String> checkDuplicatedId() {

    }
}
