package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "ADJUST_POINTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdjustPoints extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1)
    private long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

}
