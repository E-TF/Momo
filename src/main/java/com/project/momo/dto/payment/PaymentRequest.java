package com.project.momo.dto.payment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PaymentRequest {

    private String cardNumber;
    private String companyName;
    private String password;
    private LocalDate validityPeriod;
}
