package com.project.momo.entity;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "PAY_CLUB_FEE")
@DynamicInsert
public class PayClubFee extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1)
    @NotNull
    private int amount;

    @Column(name = "pay_date")
    @NotNull
    private LocalDate payDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

}
