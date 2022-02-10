package com.project.momo.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.momo.dto.login.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl);
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        try {
            LoginForm loginForm = objectMapper.readValue(request.getReader().lines().collect(Collectors.joining()), LoginForm.class);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginForm.getLoginId(), loginForm.getPassword());
            auth.setDetails(request);

            return this.getAuthenticationManager().authenticate(auth);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException Occurred!!(LoginAuthenticationFilter.attemptAuthentication().line33");
        }

        return null;
    }
}


