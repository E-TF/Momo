package com.project.momo.security.oauth.dto;

import com.project.momo.entity.Member;
import com.project.momo.security.consts.OauthType;
import lombok.Getter;

@Getter
public abstract class OAuthAttributes {

    private String name;
    private String email;
    private String imageUrl;
    private OauthType oauthType;
    private long oauthId;
    private String nameAttributeKey;

    protected OAuthAttributes(String name, String email, String imageUrl, OauthType oauthType, long oauthId, String nameAttributeKey) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.oauthType = oauthType;
        this.oauthId = oauthId;
        this.nameAttributeKey = nameAttributeKey;
    }

    public Member toMember() {
        return Member.ofOauth(oauthType, oauthId, name, email);
    }

}
