package com.project.momo.dto.member;

import com.project.momo.entity.Member;
import lombok.Getter;

@Getter
public class MemberSimpleInfoResponse {
    private long id;
    private String name;
    private String imageUrl;

    public MemberSimpleInfoResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.imageUrl = member.getImageUrl();
    }
}
