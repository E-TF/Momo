package com.project.momo.dto.payment;

import com.project.momo.entity.Payment;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PaymentListResponse {

    private List<PaymentResponse> responseList;

    public PaymentListResponse(List<Payment> paymentList) {
        responseList = new ArrayList<>();
        paymentList.stream()
                .map(PaymentResponse::new)
                .forEach(pr -> responseList.add(pr));
    }
}
