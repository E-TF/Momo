package com.project.momo.security.oauth;

import com.project.momo.security.consts.OauthType;
import lombok.Getter;

@Getter
public abstract class OAuthAttributes {

    private OauthType oauthType;
    private long oauthId;
    private String name;
    private String email;
    private String imageUrl;


    protected OAuthAttributes(OauthType oauthType, long oauthId, String name, String email, String imageUrl) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.oauthType = oauthType;
        this.oauthId = oauthId;
    }
}
