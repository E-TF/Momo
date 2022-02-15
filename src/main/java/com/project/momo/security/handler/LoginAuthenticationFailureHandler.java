package com.project.momo.security.handler;

import com.project.momo.utils.ResponseManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        String message;
        if (exception instanceof BadCredentialsException) {
            message = "비밀번호를 잘못 입력했습니다.";
        } else {
            message = exception.getMessage();
        }

        ResponseManager.sendError(response, message, HttpServletResponse.SC_BAD_REQUEST);
    }
}
