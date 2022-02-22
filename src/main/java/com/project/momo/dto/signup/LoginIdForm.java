package com.project.momo.dto.signup;

import com.project.momo.common.annotation.LoginId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginIdForm {

    @LoginId
    private String loginId;
}
