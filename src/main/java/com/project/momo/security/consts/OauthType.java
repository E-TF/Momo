package com.project.momo.security.consts;

import com.project.momo.common.exception.auth.InvalidOauthTypeException;

public enum OauthType {
    GOOGLE("google"), GITHUB("github"), KAKAO("kakao"), NAVER("naver");

    private final String value;

    OauthType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static OauthType get(String value) {
        for (OauthType oauthType : OauthType.values()) {
            if (oauthType.value.equals(value)) {
                return oauthType;
            }
        }
        throw new InvalidOauthTypeException(value);
    }
}
