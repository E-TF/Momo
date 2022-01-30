package com.project.momo.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "PAYMENT")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_name", length = 20)
    private String companyName;

    @Column(name = "card_number", length = 20)
    private String cardNumber;

    @Column(name = "validity_period", columnDefinition = "DATE")
    private LocalDate validityPeriod;

    private String password;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
