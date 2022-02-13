package com.project.momo.utils;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class ResponseManager {

    private static final String TIMESTAMP = "timestamp";

    public static void sendError(HttpServletResponse response, JSONObject jsonObject, int sc) throws IOException {
        jsonObject.put(TIMESTAMP, LocalDateTime.now());

        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(sc);
    }

    public static void setResponseToken(HttpServletResponse response, String token) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(HttpHeaders.AUTHORIZATION, token);
    }
}
