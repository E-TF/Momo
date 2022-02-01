package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "REFUND_POINTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundPoints extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1000)
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
