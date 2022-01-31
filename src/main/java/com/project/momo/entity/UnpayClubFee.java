package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "UNPAY_CLUB_FEE")
public class UnpayClubFee extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "unpay_date")
    @NotNull
    private LocalDate unpayDate;

    @Column(name = "has_paid")
    private boolean hasPaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;
}
