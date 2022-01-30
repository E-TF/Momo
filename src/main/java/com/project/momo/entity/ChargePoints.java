package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CHARGE_POINTS")
public class ChargePoints extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1000)
    @NotNull
    private int amount;

    @Column(name = "card_approval_number", length = 45)
    @Size(max = 45)
    @NotBlank
    private String cardApprovalNumber;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


}
