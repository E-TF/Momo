package com.project.momo.security.jwt;

import com.project.momo.security.consts.TokenType;
import com.project.momo.security.userdetails.UserDetailsImpl;
import com.project.momo.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final AuthorizationService authorizationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Long memberId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        String accessToken = tokenProvider.createToken(memberId, TokenType.ACCESS);
        String refreshToken = tokenProvider.createToken(memberId, TokenType.REFRESH);
        authorizationService.saveRefreshToken(memberId, refreshToken);

        sendAccessAndRefreshToken(response, accessToken, refreshToken);
    }

    private void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setHeader(TokenType.ACCESS.getTokenHeader(), accessToken);
        response.setHeader(TokenType.REFRESH.getTokenHeader(), refreshToken);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
