package com.project.momo.dto.member;

import com.project.momo.common.annotation.PhoneNumber;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberPhoneNumberUpdateRequest {
    @PhoneNumber
    String phoneNumber;
}
