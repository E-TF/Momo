package com.project.momo.security.oauth;

import com.project.momo.security.consts.OauthType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class OAuthAttributes {

    private String oauthType;

    private String oauthId;

    private String name;

    private String email;

    private String imageUrl;

}
