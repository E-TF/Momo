package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "MEMBER_POINTS_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPointsHistory extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_key")
    @NotNull
    private Code code;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String description;

    private long amount;

    @Column(name = "points_before")
    @Min(0)
    private long pointsBefore;

    @Column(name = "points_after")
    @Min(0)
    private long pointsAfter;

}
