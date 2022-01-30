package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAY_CLUB_FEE")
public class PayClubFee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int amount;

    @Column(name = "pay_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime payAt;

    @Column(name = "pay_date", columnDefinition = "DATE")
    private LocalDate payDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;


}
