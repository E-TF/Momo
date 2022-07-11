package com.project.momo.dto.payment;

import com.project.momo.entity.Member;
import com.project.momo.entity.Payment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class PaymentRequest {

    private String cardNumber;
    private String companyName;
    private String password;
    private LocalDate validityPeriod;

    public Payment toPayment(Member member) {
        return Payment.createPayment(companyName, cardNumber, validityPeriod, password, member);
    }
}
