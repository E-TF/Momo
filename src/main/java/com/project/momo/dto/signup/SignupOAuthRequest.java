package com.project.momo.dto.signup;

import com.project.momo.common.annotation.*;
import com.project.momo.security.consts.OauthType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupOAuthRequest {

    @MemberName
    private String name;

    @MemberEmail
    private String email;

    private String imageUrl;

    @PhoneNumber
    private String phoneNumber;

    private String oauthId;

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
