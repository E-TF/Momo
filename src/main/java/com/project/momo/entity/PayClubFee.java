package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "PAY_CLUB_FEE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayClubFee extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1)
    private int amount;

    @Column(name = "pay_date")
    @NotNull
    private LocalDate payDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

}
