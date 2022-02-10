package com.project.momo.utils;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class ResponseManager {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String APPLICATION_JSON = "application/json";
    private static final String UTF_8 = "UTF-8";
    private static final String TIMESTAMP = "timestamp";

    public static void sendError(HttpServletResponse response, JSONObject jsonObject, int sc) throws IOException {
        jsonObject.put(TIMESTAMP, LocalDateTime.now());

        PrintWriter out = response.getWriter();
        out.print(jsonObject);

        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);
        response.setStatus(sc);
    }

    public static void setResponseToken(HttpServletResponse response, String token) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(AUTHORIZATION_HEADER, token);
    }
}
