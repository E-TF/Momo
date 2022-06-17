package com.project.momo.dto.payment;

import com.project.momo.entity.Payment;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PaymentResponse {

    private long id;
    private String companyName;
    private String cardNumber;
    private LocalDate validityPeriod;

    public PaymentResponse(Payment payment){
        this.id = payment.getId();
        this.companyName = payment.getCompanyName();
        this.cardNumber = payment.getCardNumber();
        this.validityPeriod = payment.getValidityPeriod();
    }

}
