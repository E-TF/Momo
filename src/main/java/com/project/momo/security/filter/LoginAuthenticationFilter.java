package com.project.momo.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.project.momo.common.component.ResponseError;
import com.project.momo.dto.login.LoginForm;
import com.project.momo.security.jwt.LoginAuthenticationFailureHandler;
import com.project.momo.security.jwt.LoginAuthenticationSuccessHandler;
import com.project.momo.common.utils.RequestWrapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private final ResponseError responseError;

    public LoginAuthenticationFilter(ObjectMapper objectMapper,
                                     ResponseError responseError,
                                     @Lazy AuthenticationManager authenticationManager,
                                     LoginAuthenticationSuccessHandler successHandler,
                                     LoginAuthenticationFailureHandler failureHandler) {
        super("/api/login", authenticationManager);
        this.objectMapper = objectMapper;
        this.responseError = responseError;
        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        try {
            RequestWrapper wrapper = new RequestWrapper(request);
            LoginForm loginForm = objectMapper.readValue(wrapper.getReader().lines().collect(Collectors.joining()), LoginForm.class);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginForm.getLoginId(), loginForm.getPassword());
            auth.setDetails(request);

            return this.getAuthenticationManager().authenticate(auth);
        } catch (JsonProcessingException e) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(responseError.toJson(e));
        }
        return null;
    }
}


