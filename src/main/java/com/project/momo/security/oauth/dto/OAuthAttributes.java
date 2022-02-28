package com.project.momo.security.oauth.dto;

import com.project.momo.entity.Member;
import com.project.momo.security.consts.OauthType;
import lombok.Getter;

@Getter
public abstract class OAuthAttributes {

    private OauthType oauthType;
    private String nameAttributeKey;
    private long oauthId;
    private String name;
    private String email;
    private String imageUrl;


    protected OAuthAttributes(OauthType oauthType, String nameAttributeKey, long oauthId, String name, String email, String imageUrl) {
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
