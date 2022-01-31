package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ADJUST_POINTS")
public class AdjustPoint extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1)
    @NotNull
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memebr_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

}
