package com.project.momo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.momo.dto.error.ErrorMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class ResponseManager {

    private static ObjectMapper staticObjectMapper;
    private final ObjectMapper objectMapper;

    public static void sendError(HttpServletResponse response, String message, int sc) throws IOException {
        String json = staticObjectMapper.writeValueAsString(new ErrorMessageDto(message));
        PrintWriter out = response.getWriter();
        out.print(json);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(sc);
    }

    public static void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        sendAccessToken(response, accessToken);
        response.setHeader(JwtUtils.REFRESH_TOKEN_HEADER, refreshToken);
    }

    public static void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @PostConstruct
    public void postConstruct() {
        staticObjectMapper = objectMapper;
    }
}
