package com.project.momo.dto.signup;

import com.project.momo.common.annotation.*;
import com.project.momo.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
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

    public Member toMember() {
        return Member.createMember(loginId, password, name, email, phoneNumber);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
