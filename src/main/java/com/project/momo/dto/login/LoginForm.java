package com.project.momo.dto.login;

import com.project.momo.common.annotation.LoginId;
import com.project.momo.common.annotation.Password;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginForm {

    @LoginId
    private String loginId;

    @Password
    private String password;

}
