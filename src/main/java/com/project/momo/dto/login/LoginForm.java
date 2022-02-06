package com.project.momo.dto.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class LoginForm {

    @Size(min = 3, max = 45)
    @NotBlank
    private String loginId;

    @NotBlank
    @Size(min = 11, max = 20)
    private String password;

}
