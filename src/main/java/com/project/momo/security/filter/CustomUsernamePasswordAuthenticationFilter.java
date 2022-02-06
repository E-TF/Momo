package com.project.momo.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.momo.dto.login.LoginForm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String json = "";
        LoginForm loginForm = null;

        try {
            loginForm = objectMapper.readValue(
                    request.getReader().lines().collect(Collectors.joining()),
                    LoginForm.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException occurred");
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred, Maybe it's not json type");
        }

        String username = loginForm.getLoginId();
        String password = loginForm.getPassword();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
        System.out.println("isAuthenticated : " + authenticate.isAuthenticated());
        System.out.println(authenticate.getAuthorities());
        System.out.println(authenticate.getCredentials());
        System.out.println(authenticate.getDetails());
        System.out.println(authenticate.getPrincipal());
        System.out.println(authenticate.getName());

        return authenticate;
    }
}
