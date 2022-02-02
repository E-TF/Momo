package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "PAYMENT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    public static Payment createPayment(String companyName, String cardNumber, LocalDate validityPeriod, String password, Member member) {
        Payment payment = new Payment();
        payment.companyName = companyName;
        payment.cardNumber = cardNumber;
        payment.validityPeriod = validityPeriod;
        payment.password = password;
        payment.member = member;
        payment.createdBy = member.getLoginId();

        return payment;
    }
}
