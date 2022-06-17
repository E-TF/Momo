package com.project.momo.dto.member;

import com.project.momo.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoResponse {

    private String loginId;
    private String name;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private long points;
    private int paymentCnt;

    public MemberInfoResponse(Member member){
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.imageUrl = member.getImageUrl();
        this.points = member.getPoints();
        this.paymentCnt = member.getPaymentCnt();
    }
}
