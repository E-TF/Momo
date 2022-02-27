package com.project.momo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

@RestController
public class OAuthController {

    @GetMapping("/login/oauth2/code/kakao")
    public void apiKakaoGet(HttpServletRequest request) throws IOException {
        System.out.println("OAuthController.apiKakaoGet");
        System.out.println(request.getRequestURI());
        String s = request.getReader().readLine();
        System.out.println(s);
        Iterator<String> stringIterator1 = request.getHeaderNames().asIterator();
        for (Iterator<String> it = stringIterator1; it.hasNext(); ) {
            String header = it.next();
            System.out.println(header + " : " + request.getHeader(header));
        }

    }

    @PostMapping("/login/oauth2/code/kakao")
    public void apiKakaoPost(HttpServletRequest request) throws IOException {
        System.out.println("OAuthController.apiKakaoPost");
        String s = request.getReader().readLine();
        System.out.println(s);
        Iterator<String> stringIterator1 = request.getHeaderNames().asIterator();
        for (Iterator<String> it = stringIterator1; it.hasNext(); ) {
            String header = it.next();
            System.out.println(header + " : " + request.getHeader(header));
        }

    }

}
