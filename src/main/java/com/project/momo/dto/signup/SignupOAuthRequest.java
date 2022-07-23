package com.project.momo.dto.signup;

import com.project.momo.common.annotation.member.MemberEmail;
import com.project.momo.common.annotation.member.MemberName;
import com.project.momo.common.annotation.member.PhoneNumber;
import com.project.momo.security.consts.OauthType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupOAuthRequest {

    @MemberName
    private String name;

    @MemberEmail
    private String email;

    private String imageUrl;

    @PhoneNumber
    private String phoneNumber;

    @NotBlank
    private String oauthId;

    @NotBlank
    private String oauthType;

    public static SignupOAuthDetails toSignupOAuthDetails(SignupOAuthRequest signupOAuthRequest){
        return new SignupOAuthDetails(signupOAuthRequest.getName(),
                signupOAuthRequest.getEmail(),
                signupOAuthRequest.getImageUrl(),
                signupOAuthRequest.getPhoneNumber(),
                signupOAuthRequest.getOauthId(),
                OauthType.toOauthType(signupOAuthRequest.getOauthType()));
    }

}
