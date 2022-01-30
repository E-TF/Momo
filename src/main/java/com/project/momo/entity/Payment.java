package com.project.momo.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "PAYMENT")
@Getter
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "company_name", length = 20)
    @Size(max = 20)
    @NotBlank
    private String companyName;

    @Column(name = "card_number", length = 20)
    @Size(max = 20)
    @NotBlank
    private String cardNumber;

    @Column(name = "validity_period")
    @NotNull
    private LocalDate validityPeriod;

    @NotNull
    @Size(max = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;
}
