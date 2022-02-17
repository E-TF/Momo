package com.project.momo.security.filter;

import com.project.momo.security.jwt.TokenProvider;
import com.project.momo.utils.JwtUtils;
import com.project.momo.utils.ResponseManager;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String message = "";
        try {
            filterChain.doFilter(request, response);
        } catch (SignatureException | MalformedJwtException ae) {
            message = "잘못된 JWT 서명입니다.";
        } catch (ExpiredJwtException ae) {
            String refreshJwt = JwtUtils.resolveRefreshToken(request);

            if (StringUtils.hasText(refreshJwt)) {
                try {
                    Long memberId = ae.getClaims().get(JwtUtils.MEMBER_ID_CLAIM_NAME, Long.class);

                    if (tokenProvider.validateRefreshToken(memberId, refreshJwt)) {
                        String accessToken = tokenProvider.reissue(memberId);
                        ResponseManager.sendAccessToken(response, accessToken, HttpServletResponse.SC_CREATED);
                        return;
                    }

                } catch (SignatureException | MalformedJwtException re) {
                    message = "잘못된 Refresh JWT 서명입니다.";
                } catch (ExpiredJwtException re) {
                    message = "만료된 Refresh JWT 토큰입니다.";
                } catch (UnsupportedJwtException re) {
                    message = "지원되지 않는 Refresh JWT 토큰입니다.";
                } catch (IllegalArgumentException re) {
                    message = "Refresh JWT 토큰이 잘못되었습니다.";
                }
            } else {
                message = "만료된 JWT 토큰입니다.(send request with access token and refresh token)";
            }
        } catch (UnsupportedJwtException ae) {
            message = "지원되지 않는 JWT 토큰입니다.";
        } catch (IllegalArgumentException ae) {
            message = "JWT 토큰이 잘못되었습니다.";
        } finally {
            if(StringUtils.hasText(message))
                ResponseManager.sendError(response, new RuntimeException(message), HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
