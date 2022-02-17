package com.project.momo.dto.signup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginIdForm {

    @Size(min = 3, max = 45)
    @NotBlank
    private String loginId;
}
