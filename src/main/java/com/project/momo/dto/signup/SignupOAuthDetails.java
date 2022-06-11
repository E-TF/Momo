package com.project.momo.dto.signup;

import com.project.momo.common.annotation.MemberEmail;
import com.project.momo.common.annotation.MemberName;
import com.project.momo.common.annotation.PhoneNumber;
import com.project.momo.entity.Member;
import com.project.momo.security.consts.OauthType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupOAuthDetails {

    @MemberName
    private String name;

    @MemberEmail
    private String email;

    private String imageUrl;

    @PhoneNumber
    private String phoneNumber;

    private String oauthId;

    private OauthType oauthType;

    public Member toMember(){
        return Member.createOauth(oauthType, oauthId, name, email, phoneNumber, imageUrl);
    }
}
