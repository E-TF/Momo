package com.project.momo.dto.login;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginForm {

    @Size(min = 3, max = 45)
    @NotBlank
    private String loginId;

    @NotBlank
    @Size(min = 11, max = 20)
    private String password;

}
