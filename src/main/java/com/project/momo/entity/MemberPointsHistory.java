package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER_POINTS_HISTORY")
public class MemberPointsHistory extends BaseEntity {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "code_key")
    private Code code;

    @Column(length = 45)
    private String description;

    private int amount;

    @Column(name = "points_before")
    private int pointsBefore;

    @Column(name = "points_after")
    private int pointsAfter;

}
