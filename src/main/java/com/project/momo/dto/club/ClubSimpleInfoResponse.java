package com.project.momo.dto.club;

import com.project.momo.entity.Club;
import lombok.Getter;

@Getter
public class ClubSimpleInfoResponse {
    private long id;
    private String name;
    private String description;
    private String imageUrl;

    public ClubSimpleInfoResponse(Club club) {
        this.id = club.getId();
        this.name = club.getName();
        this.description = club.getDescription();
        this.imageUrl = club.getImageUrl();
    }
}
