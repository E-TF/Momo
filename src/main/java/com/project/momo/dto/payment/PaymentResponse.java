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

    public static PaymentResponse createPaymentResponse(Payment payment) {
        PaymentResponse paymentResponse = new PaymentResponse();

        paymentResponse.id = payment.getId();
        paymentResponse.companyName = payment.getCompanyName();
        paymentResponse.cardNumber = payment.getCardNumber();
        paymentResponse.validityPeriod = payment.getValidityPeriod();
        return paymentResponse;
    }

}
