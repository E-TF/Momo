package com.project.momo.security.filter;

import com.project.momo.security.jwt.TokenProvider;
import com.project.momo.utils.JsonConverter;
import com.project.momo.utils.JwtUtils;
import com.project.momo.utils.ResponseManager;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtExceptionFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String message = "";
        try {
            chain.doFilter(request, response);
        } catch (SignatureException | MalformedJwtException exception) {
            message = "잘못된 JWT 서명입니다.";
        } catch (ExpiredJwtException exception) {
            String refreshJwt = JwtUtils.resolveRefreshToken((HttpServletRequest) request);

            if (StringUtils.hasText(refreshJwt)) {
                try{
                    Long memberId = exception.getClaims().get(JwtUtils.MEMBER_ID_CLAIM_NAME, Long.class);

                    if (tokenProvider.validateRefreshToken(memberId, refreshJwt)) {
                        String accessToken = tokenProvider.reissue(memberId);
                        ResponseManager.sendAccessToken((HttpServletResponse) response, accessToken);
                        return;
                    } else {
                        message = "유효하지 않은 Refresh JWT 토큰입니다.";
                    }
                } catch (SignatureException | MalformedJwtException e) {
                    message = "잘못된 Refresh JWT 서명입니다.";
                } catch (ExpiredJwtException e) {
                    message = "만료된 Refresh JWT 토큰입니다.";
                } catch (UnsupportedJwtException e) {
                    message = "지원되지 않는 Refresh JWT 토큰입니다.";
                } catch (IllegalArgumentException e) {
                    message = "Refresh JWT 토큰이 잘못되었습니다.";
                }
            } else {
                message = "만료된 JWT 토큰입니다.(send request with access token and refresh token)";
            }
        } catch (UnsupportedJwtException exception) {
            message = "지원되지 않는 JWT 토큰입니다.";
        } catch (IllegalArgumentException exception) {
            message = "JWT 토큰이 잘못되었습니다.";
        }

        ResponseManager.sendError((HttpServletResponse) response, message, HttpServletResponse.SC_BAD_REQUEST);
    }
}
