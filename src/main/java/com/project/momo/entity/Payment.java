package com.project.momo.entity;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
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

    @Column(name = "company_name")
    @Size(max = 20)
    @NotBlank
    private String companyName;

    @Column(name = "card_number")
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

    public void update(String companyName, String cardNumber, LocalDate validityPeriod) {
        this.companyName = companyName;
        this.cardNumber = cardNumber;
        this.validityPeriod = validityPeriod;
    }

    public void checkMemberAuth(Member member) {
        if (!member.getId().equals(this.member.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTHORIZATION);
        }
    }
}
