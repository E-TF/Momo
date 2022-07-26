package com.project.momo.dto.member;

import com.project.momo.common.annotation.member.MemberName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberNameUpdateRequest {
    @MemberName
    String name;
}
