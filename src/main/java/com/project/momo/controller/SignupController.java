package com.project.momo.controller;

import com.project.momo.dto.signup.LoginIdForm;
import com.project.momo.dto.signup.SignupForm;
import com.project.momo.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members/")
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/new")
    public void signup(@RequestBody @Valid SignupForm signupForm) {
        signupService.signup(signupForm);
    }

    @GetMapping("/checkDuplicateLoginId")
    public ResponseEntity<Boolean> checkDuplicateLoginId(@RequestBody @Valid LoginIdForm loginIdForm) {
        boolean hasDuplicateLoginId = signupService.hasDuplicateLoginId(loginIdForm.getLoginId());
        return ResponseEntity.status(HttpStatus.OK).body(!hasDuplicateLoginId);
    }
}
