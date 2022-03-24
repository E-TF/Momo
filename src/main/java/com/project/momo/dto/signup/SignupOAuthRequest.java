package com.project.momo.dto.signup;

import com.project.momo.common.annotation.*;
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

    @OauthType
    private String oauthType;

}
