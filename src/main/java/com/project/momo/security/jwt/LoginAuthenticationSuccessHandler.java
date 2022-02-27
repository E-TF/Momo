package com.project.momo.security.jwt;

import com.project.momo.repository.MemberRepository;
import com.project.momo.security.consts.OauthType;
import com.project.momo.security.consts.TokenType;
import com.project.momo.security.userdetails.UserDetailsImpl;
import com.project.momo.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final AuthorizationService authorizationService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Long memberId;
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication1.getClass());
        System.out.println(authentication1);
        if (authentication instanceof OAuth2AuthenticationToken) {
            System.out.println("LoginAuthenticationSuccessHandler.onAuthenticationSuccess instanceof 안타야 성공인거임");
            OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
            memberId = memberRepository.findByOauthTypeAndOauthId(OauthType.valueOf(authToken.getPrincipal().getAttribute("type")), Long.valueOf(authToken.getName())).get().getId();
        } else {
            memberId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        }

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
