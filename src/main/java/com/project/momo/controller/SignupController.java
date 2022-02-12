package com.project.momo.controller;

import com.project.momo.dto.signup.SignupForm;
import com.project.momo.exception.DuplicatedLoginIdException;
import com.project.momo.service.SignupService;
import com.project.momo.utils.JsonConverter;
import com.project.momo.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/signup")
public class SignupController {

    private final SignupService signupService;

    @PostMapping
    public void signup(@RequestBody @Valid SignupForm signupForm) {
        signupService.signup(signupForm);
    }

    @ExceptionHandler(DuplicatedLoginIdException.class)
    public void handleDuplicatedLoginIdException(HttpServletResponse response, DuplicatedLoginIdException exception) throws IOException {
        JSONObject jsonObject = JsonConverter.stringToJson(exception.getMessage());
        ResponseManager.sendError(response, jsonObject, HttpServletResponse.SC_BAD_REQUEST);
    }
}
