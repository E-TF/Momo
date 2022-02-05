package com.project.momo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
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
    @NotBlank
    private String email;

    @Size(max = 20)
    @NotBlank
    private String phoneNumber;

}
