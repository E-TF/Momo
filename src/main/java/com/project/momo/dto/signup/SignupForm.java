package com.project.momo.dto.signup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignupForm {

    @Size(min = 3, max = 45)
    @NotBlank
    private String loginId;

    @NotBlank
    @Size(min = 11, max = 20)
    private String password;

    @Size(max = 45)
    @NotBlank
    private String name;

    @Size(max = 255)
    @Email
    @NotEmpty
    private String email;

    @Size(max = 20)
    @NotBlank
    private String phoneNumber;

}
