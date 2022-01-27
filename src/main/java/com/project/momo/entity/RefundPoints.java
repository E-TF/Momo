package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REFUND_POINTS")
public class RefundPoints extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "refund_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime refundAt;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
