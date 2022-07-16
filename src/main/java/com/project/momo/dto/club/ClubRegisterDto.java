package com.project.momo.dto.club;

import com.project.momo.entity.Category;
import com.project.momo.entity.Club;
import com.project.momo.entity.District;
import lombok.Getter;

@Getter
public class ClubRegisterDto {
    private String name;
    private String description;
    private String imageUrl;
    private Category category;
    private District district;
    private String memberLoginId;

    public ClubRegisterDto(ClubRegisterRequest request, Category category, District district, String memberLoginId) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.imageUrl = request.getImageUrl();
        this.category = category;
        this.district = district;
        this.memberLoginId = memberLoginId;
    }

    public Club toClub() {
        return Club.createClub(this.name, this.description, this.category, this.imageUrl, this.district, this.memberLoginId);
    }
}
