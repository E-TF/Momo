package com.project.momo.security.handler;

import com.project.momo.utils.JsonConverter;
import com.project.momo.utils.ResponseManager;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        if (response.isCommitted()) {
            log.info("Did not write to response since already committed");
            return;
        }

        JSONObject jsonObject = JsonConverter.stringToJson("유효하지 않은 토큰입니다.");
        ResponseManager.sendError(response, jsonObject, HttpServletResponse.SC_UNAUTHORIZED);
    }
}
