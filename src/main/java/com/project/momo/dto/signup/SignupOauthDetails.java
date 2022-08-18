package com.project.momo.dto.signup;

import com.project.momo.common.annotation.member.MemberEmail;
import com.project.momo.common.annotation.member.MemberName;
import com.project.momo.entity.Member;
import com.project.momo.security.consts.OauthType;
import com.project.momo.security.oauth.OauthAttributes;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupOauthDetails {

    private OauthType oauthType;

    private String oauthId;

    @MemberName
    private String name;

    @MemberEmail
    private String email;

    private String imageUrl;

    public SignupOauthDetails(OauthAttributes oauthAttributes) {
        this.oauthType = oauthAttributes.getOauthType();
        this.oauthId = oauthAttributes.getOauthId();
        this.name = oauthAttributes.getName();
        this.email = oauthAttributes.getEmail();
        this.imageUrl = oauthAttributes.getImageUrl();
    }

    public Member toMember(){
        return Member.createOauth(oauthType, oauthId, name, email, imageUrl);
    }
}
