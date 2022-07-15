package com.project.momo.dto.club;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubRegisterRequest {
    private String name;
    private String description;
    private long categoryId;
    private String imageUrl;
    private long districtId;
}
