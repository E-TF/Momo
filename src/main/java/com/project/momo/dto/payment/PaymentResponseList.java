package com.project.momo.dto.payment;

import com.project.momo.entity.Payment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentResponseList {

    private List<PaymentResponse> responseList;

    public static PaymentResponseList createPaymentResponseList(List<Payment> paymentList) {
        PaymentResponseList paymentResponseList = new PaymentResponseList();
        paymentList.stream()
                .map(PaymentResponse::createPaymentResponse)
                .forEach(pr -> paymentResponseList.responseList.add(pr));
        return paymentResponseList;
    }
}
