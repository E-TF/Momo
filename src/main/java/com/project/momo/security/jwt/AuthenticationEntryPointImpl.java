package com.project.momo.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.momo.common.exception.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (response.isCommitted()) {
            log.error("Did not write to response since already committed\n" +
                            "Request URI : {}\n" +
                            "Http Method : {}\n" +
                            "Location : {}\n" +
                            "Unhandled Exception : {}",
                    request.getRequestURI(), request.getMethod(), this.getClass(), authException.getClass().getSimpleName());
            return;
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorDto(authException)));
    }
}
