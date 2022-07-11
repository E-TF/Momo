package com.project.momo.dto.payment;

import com.project.momo.entity.Payment;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PaymentResponse {

    private long id;
    private String companyName;
    private String cardNumber;
    private LocalDate validityPeriod;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.companyName = payment.getCompanyName();
        this.cardNumber = payment.getCardNumber();
        this.validityPeriod = payment.getValidityPeriod();
    }

}
