package com.project.momo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ResponseManager {
    private static JsonConverter staticJsonConverter;
    private final JsonConverter jsonConverter;

    public static void sendError(HttpServletResponse response, Exception exception, int sc) throws IOException {
        setDefault(response, sc);
        PrintWriter out = response.getWriter();
        String json = staticJsonConverter.convert(exception);
        out.print(json);
    }

    public static void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken, int sc) {
        sendAccessToken(response, accessToken, sc);
        response.setHeader(JwtUtils.REFRESH_TOKEN_HEADER, refreshToken);
    }

    public static void sendAccessToken(HttpServletResponse response, String accessToken, int sc) {
        setDefault(response, sc);
        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
    }

    private static void setDefault(HttpServletResponse response, int sc) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(sc);
    }

    @PostConstruct
    private void postConstruct() {
        this.staticJsonConverter = jsonConverter;
    }

}
