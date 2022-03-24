package com.project.momo.dto.member;

import com.project.momo.common.annotation.Password;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordUpdateRequest {

    @Password
    private String curPassword;

    @Password
    private String newPassword;

}
