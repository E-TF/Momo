package com.project.momo.controller;

import com.project.momo.common.utils.AuthUtils;
import com.project.momo.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/logout")
public class LogoutController {

    private final LogoutService logoutService;

    @GetMapping
    public void logout() {
        logoutService.logout(AuthUtils.getMemberId());
    }

}
