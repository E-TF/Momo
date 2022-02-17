package com.project.momo.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptUtils {

    public static String encrypt(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(password);
    }

}
