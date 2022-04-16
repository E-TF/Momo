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

    public static MemberInfoResponse createMemberInfoResponse(Member member) {
        MemberInfoResponse mir = new MemberInfoResponse();
        mir.loginId = member.getLoginId();
        mir.name = member.getName();
        mir.email = member.getEmail();
        mir.phoneNumber = member.getPhoneNumber();
        mir.imageUrl = member.getImageUrl();
        mir.points = member.getPoints();
        mir.paymentCnt = member.getPaymentCnt();

        return mir;
    }
}
