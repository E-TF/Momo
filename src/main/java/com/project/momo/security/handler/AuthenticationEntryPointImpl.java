package com.project.momo.security.handler;

import com.project.momo.exception.ResponseAlreadyCommittedException;
import com.project.momo.utils.ResponseManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (response.isCommitted()) {
            ResponseManager.sendError(response, new ResponseAlreadyCommittedException(authException), HttpServletResponse.SC_NOT_IMPLEMENTED);
            return;
        }

        ResponseManager.sendError(response, authException, HttpServletResponse.SC_UNAUTHORIZED);
    }
}
