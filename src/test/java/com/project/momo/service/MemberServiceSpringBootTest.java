package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.payment.PaymentRequest;
import com.project.momo.dto.signup.SignupRequest;
import com.project.momo.entity.Member;
import com.project.momo.repository.MemberRepository;
import com.project.momo.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceSpringBootTest {

    private final String MEMBER_LOGIN_ID = "testId";
    @Autowired
    private MemberService memberService;
    @Autowired
    private SignupService signupService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Value("${service.member.max-payment-count}")
    private short MAX_PAYMENT_CNT;

    private Member member;
    private PaymentRequest paymentRequest;

    @BeforeEach
    void beforeEach() {
        signupService.signup(new SignupRequest(MEMBER_LOGIN_ID, "testPW", "testName", "test@test.com", "010-1234-5678"));
        member = memberRepository.findByLoginId(MEMBER_LOGIN_ID).get();
        paymentRequest = new PaymentRequest("CARD_NUMBER", "COMPANY_NAME", "PASSWORD", LocalDate.now());
    }

    @Test
    @DisplayName("사용자가 등록한 결제수단이 MAX_PAYMENT_CNT 개 일 경우, 새로운 결제수단 등록 시 BusinessException 이 발생한다.")
    void registerNewPaymentThrowsBusinessExceptionTest() {
        //given
        for (int i = 0; i < MAX_PAYMENT_CNT; i++) {
            memberService.registerNewPayment(member.getId(), paymentRequest);
        }

        //when & then
        try {
            memberService.registerNewPayment(member.getId(), paymentRequest);
        } catch (BusinessException e) {
            assertEquals(ErrorCode.EXCEED_PAYMENT_CNT_LIMIT, e.getErrorCode());
            return;
        }
        fail();
    }

    @Test
    @DisplayName("사용자가 결제수단을 등록하면, 사용자(Member)의 결제수단 수(paymentCnt)가 1 증가한다.")
    void registerNewPaymentSuccessTest() {
        //when
        memberService.registerNewPayment(member.getId(), new PaymentRequest("CARD_NUMBER", "COMPANY_NAME", "PASSWORD", LocalDate.now()));

        //then
        assertEquals(member.getPaymentCnt(), 1);

    }

    @Test
    @DisplayName("사용자가 계정을 삭제하면, 사용자의 결제수단(Payment)가 모두 삭제된다.")
    void withdrawalDeleteAllPaymentTest() {
        //given
        for (int i = 0; i < MAX_PAYMENT_CNT; i++) {
            memberService.registerNewPayment(member.getId(), paymentRequest);
        }

        //when
        memberService.withdraw(member.getId());

        //then
        assertAll(
                () -> assertEquals(memberRepository.findById(member.getId()), Optional.empty()),
                () -> assertEquals(paymentRepository.findAllByMemberId(member.getId()), Collections.emptyList())
        );
    }
}
