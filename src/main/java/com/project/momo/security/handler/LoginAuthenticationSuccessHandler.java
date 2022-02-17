package com.project.momo.security.handler;

import com.project.momo.enums.TokenType;
import com.project.momo.security.jwt.TokenProvider;
import com.project.momo.security.userdetails.UserDetailsImpl;
import com.project.momo.service.AuthorizationService;
import com.project.momo.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final AuthorizationService authorizationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = tokenProvider.createToken(authentication, TokenType.ACCESS);
        String refreshToken = tokenProvider.createToken(authentication, TokenType.REFRESH);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        authorizationService.saveRefreshToken(userDetails.getId(), refreshToken);

        ResponseManager.sendAccessAndRefreshToken(response, accessToken, refreshToken, HttpServletResponse.SC_CREATED);
    }
}
