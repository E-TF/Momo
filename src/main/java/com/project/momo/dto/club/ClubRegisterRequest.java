package com.project.momo.dto.club;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubRegisterRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Min(0)
    private long categoryId;
    @Min(0)
    private long districtId;
    private String imageUrl;
}
