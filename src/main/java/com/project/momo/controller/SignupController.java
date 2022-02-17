package com.project.momo.controller;

import com.project.momo.dto.signup.LoginIdForm;
import com.project.momo.dto.signup.SignupForm;
import com.project.momo.exception.DuplicatedLoginIdException;
import com.project.momo.service.SignupService;
import com.project.momo.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/signup")
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/member")
    public void signup(@RequestBody @Valid SignupForm signupForm) {
        signupService.signup(signupForm);
    }

    @PostMapping("/checkDuplicateLoginId")
    public ResponseEntity<Boolean> checkDuplicateLoginId(@RequestBody @Valid LoginIdForm loginIdForm) {
        boolean hasDuplicateLoginId = signupService.hasDuplicateLoginId(loginIdForm.getLoginId());

        return new ResponseEntity<>(!hasDuplicateLoginId, HttpStatus.OK);
    }

    @ExceptionHandler(DuplicatedLoginIdException.class)
    public void handleDuplicatedLoginIdException(HttpServletResponse response, DuplicatedLoginIdException exception) throws IOException {
        ResponseManager.sendError(response, exception, HttpServletResponse.SC_BAD_REQUEST);
    }
}
