package com.project.momo.dto.payment;

import com.project.momo.entity.Payment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentListResponse {

    private List<PaymentResponse> responseList;

    public PaymentListResponse(List<Payment> paymentList) {
        responseList = new ArrayList<PaymentResponse>();
        PaymentListResponse paymentListResponse = new PaymentListResponse();
        paymentList.stream()
                .map(PaymentResponse::new)
                .forEach(pr -> paymentListResponse.responseList.add(pr));
    }
}
