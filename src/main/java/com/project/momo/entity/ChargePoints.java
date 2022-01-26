package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CHARGE_POINTS")
public class ChargePoints {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int amount;

    @Column(name = "charge_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime chargeAt;

    @Column(name = "card_approval_number", length = 45)
    private String cardApprovalNumber;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20)
    private String createdBy;
}
