package com.project.momo.controller;

import com.project.momo.common.annotation.LoginId;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.signup.SignupOAuthRequest;
import com.project.momo.dto.signup.SignupRequest;
import com.project.momo.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/signup")
@Validated
public class SignupController {

    private final SignupService signupService;

    @PostMapping
    public void signup(@RequestBody @Valid SignupRequest signupRequest) {
        signupService.signup(signupRequest);
    }

    @PostMapping("/oauth")
    public void signupOAuth(@RequestBody @Valid SignupOAuthRequest signupOAuthRequest) {
        signupService.signupOAuth(SignupOAuthRequest.toSignupOAuthDetails(signupOAuthRequest));
    }

    @GetMapping("/loginid/duplicate")
    public ResponseEntity<?> checkDuplicateLoginId(@RequestParam @LoginId String loginId) {
        signupService.checkDuplicateLoginId(loginId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException() {
        return ResponseEntity.status(ErrorCode.INVALID_LOGIN_ID.getHttpStatus()).body(ErrorCode.INVALID_LOGIN_ID.getMessage());
    }
}
