package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CHARGE_POINTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChargePoints extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1000)
    private long amount;

    @Column(name = "card_approval_number")
    @Size(max = 45)
    @NotBlank
    private String cardApprovalNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
