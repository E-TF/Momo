package com.project.momo.security.consts;

public enum OauthType {
    NONE(""), GOOGLE("google"), GITHUB("github"), KAKAO("kakao"), NAVER("naver");

    String registrationId;

    OauthType(String registrationId) {
        this.registrationId = registrationId;
    }

    public static OauthType get(String registrationId) {
        OauthType[] values = OauthType.values();
        for (OauthType value : values) {
            if (registrationId.equals(value.registrationId)) {
                return value;
            }
        }
        return null;
    }

    public boolean equals(String registrationId) {
        return this.registrationId.equals(registrationId);
    }
}
