package com.project.momo.dto.signup;

import com.project.momo.common.annotation.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignupRequest {

    @LoginId
    private String loginId;

    @Password
    private String password;

    @MemberName
    private String name;

    @MemberEmail
    private String email;

    @PhoneNumber
    private String phoneNumber;

}
