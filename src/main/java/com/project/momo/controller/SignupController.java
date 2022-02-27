package com.project.momo.controller;

import com.project.momo.common.annotation.LoginId;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.signup.SignupForm;
import com.project.momo.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
@Validated
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid SignupForm signupForm) {
        signupService.signup(signupForm);
    }

    @GetMapping("/loginid/duplicate")
    public ResponseEntity<Boolean> checkDuplicateLoginId(@RequestParam @LoginId String loginId) {
        System.out.println(loginId);
        if (signupService.hasDuplicateLoginId(loginId))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException() {
        return ResponseEntity.status(ErrorCode.INVALID_LOGIN_ID.getHttpStatus()).body(ErrorCode.INVALID_LOGIN_ID.getMessage());
    }
}
