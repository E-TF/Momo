package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "UNPAY_CLUB_FEE")
public class UnpayClubFee extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "unpay_date", columnDefinition = "DATE")
    private LocalDate unpayDate;

    @Column(name = "has_paid", columnDefinition = "TINYINT(1)")
    private boolean hasPaid;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
