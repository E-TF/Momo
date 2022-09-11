package com.project.momo.security.consts;

import com.project.momo.common.exception.auth.InvalidOauthTypeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OauthType {
    GOOGLE("google"), GITHUB("github"), KAKAO("kakao"), NAVER("naver");

    private final String value;

    OauthType(String value) {
        this.value = value;
    }

    public static OauthType toOauthType(String value){
        return Arrays.stream(OauthType.values()).filter(oauthType -> oauthType.value.equals(value)).findFirst().orElseThrow(() -> new InvalidOauthTypeException(value));
    }
}
