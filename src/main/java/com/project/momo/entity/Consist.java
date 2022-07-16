package com.project.momo.entity;

import com.project.momo.common.constatnt.ClubRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CONSIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Consist extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ClubRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    public static Consist createConsist(Member member, Club club, ClubRole role) {
        Consist consist = new Consist();
        consist.member = member;
        consist.club = club;
        consist.role = role;
        consist.createdBy = member.getLoginId();
        return consist;
    }
}
