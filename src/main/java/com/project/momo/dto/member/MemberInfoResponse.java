package com.project.momo.dto.member;

import com.project.momo.entity.Member;
import lombok.Getter;

@Getter
public class MemberInfoResponse {

    private String loginId;
    private String name;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private long points;
    private int paymentCnt;

    public MemberInfoResponse(Member member, int paymentCnt) {
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.imageUrl = member.getImageUrl();
        this.points = member.getPoints();
        this.paymentCnt = paymentCnt;
    }
}
