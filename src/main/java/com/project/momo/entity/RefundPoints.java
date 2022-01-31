package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "REFUND_POINTS")
public class RefundPoints extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1000)
    @NotNull
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
