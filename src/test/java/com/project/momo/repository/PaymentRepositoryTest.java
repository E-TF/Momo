package com.project.momo.repository;

import com.project.momo.entity.Member;
import com.project.momo.entity.Payment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class PaymentRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    void 결제수단저장() {
        //given
        Member member = Member.createMember("loginId", "password",
                "test", "test@test.com", "00000000000", null);
        Payment payment = Payment.createPayment("테스트은행",
                "111111-11-111111", LocalDate.now(), "password", member);

        //when
        memberRepository.save(member);
        paymentRepository.save(payment);

        //then
        Payment findPayment = paymentRepository.findById(payment.getId()).orElseThrow(IllegalStateException::new);
        List<Payment> payments = paymentRepository.findAll();

        Assertions.assertThat(findPayment).isEqualTo(payment);
        Assertions.assertThat(findPayment.getMember()).isEqualTo(member);
        Assertions.assertThat(payments).contains(payment);
        Assertions.assertThat(payments.size()).isEqualTo(1);
    }
}