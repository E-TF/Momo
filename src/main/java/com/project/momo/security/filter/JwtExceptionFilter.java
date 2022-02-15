package com.project.momo.security.filter;

import com.project.momo.utils.JsonConverter;
import com.project.momo.utils.ResponseManager;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.json.JSONObject;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String message = "";
        try {
            chain.doFilter(request, response);
        } catch (SignatureException | MalformedJwtException e) {
            message = "잘못된 JWT 서명입니다.";
        } catch (ExpiredJwtException e) {
            message = "만료된 JWT 토큰입니다.";
        } catch (UnsupportedJwtException e) {
            message = "지원되지 않는 JWT 토큰입니다.";
        } catch (IllegalArgumentException e) {
            message = "JWT 토큰이 잘못되었습니다.";
        }
        JSONObject jsonObject = JsonConverter.stringToJson(message);
        ResponseManager.sendError((HttpServletResponse) response, jsonObject, HttpServletResponse.SC_BAD_REQUEST);
    }
}
