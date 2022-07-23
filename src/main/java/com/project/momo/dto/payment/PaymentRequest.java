package com.project.momo.dto.payment;

import com.project.momo.entity.Member;
import com.project.momo.entity.Payment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentRequest {
    @NotBlank
    private String cardNumber;
    @NotBlank
    private String companyName;
    @NotBlank
    private String password;
    @NotNull
    private LocalDate validityPeriod;

    public Payment toPayment(Member member) {
        return Payment.createPayment(companyName, cardNumber, validityPeriod, password, member);
    }
}
