package com.project.momo.security.filter;

import com.project.momo.security.consts.TokenType;
import com.project.momo.common.exception.JwtException;
import com.project.momo.security.jwt.TokenProvider;
import com.project.momo.common.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws JwtException, ServletException, IOException {
        if (JwtUtils.hasToken(request, TokenType.REFRESH)) {
            String refreshJwt = JwtUtils.resolveToken(request, TokenType.REFRESH);
            Long memberId = tokenProvider.validateRefreshToken(refreshJwt);
            String reissueToken = tokenProvider.reissue(memberId);

            sendAccessToken(response, reissueToken);
            return;
        }

        if (JwtUtils.hasToken(request, TokenType.ACCESS)) {
            String accessJwt = JwtUtils.resolveToken(request, TokenType.ACCESS);
            tokenProvider.validate(accessJwt);
            Authentication authentication = tokenProvider.getAuthentication(accessJwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private void sendAccessToken(HttpServletResponse response, String token) {
        response.setHeader(TokenType.ACCESS.getTokenHeader(), token);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

}
