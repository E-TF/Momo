package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "REFUND_POINTS")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundPoints extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1000)
    private long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
