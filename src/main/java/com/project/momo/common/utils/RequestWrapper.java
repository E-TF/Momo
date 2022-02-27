package com.project.momo.common.utils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class RequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = request.getInputStream().readAllBytes();

    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.newBIAS()));
    }

    @Override
    public ServletInputStream getInputStream() {

        ByteArrayInputStream bais = this.newBIAS();

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return bais.available() == 0;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {}

            @Override
            public int read() {
                return bais.read();
            }
        };
    }

    private ByteArrayInputStream newBIAS() {
        return new ByteArrayInputStream(body);
    }
}
