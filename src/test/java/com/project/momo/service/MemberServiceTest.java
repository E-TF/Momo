package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.dto.member.MemberInfoResponse;
import com.project.momo.dto.payment.PaymentResponse;
import com.project.momo.entity.Member;
import com.project.momo.entity.Payment;
import com.project.momo.repository.MemberRepository;
import com.project.momo.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private Member member;
    private Payment payment;
    private MemberService memberService;
    private MemberRepository memberRepository;
    private PaymentRepository paymentRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        member = Member.createMember("TEST_ID", "TEST_PW", "TEST_NAME",
                "TEST_EMAIL", "010-1234-5678");
        payment = Payment.createPayment("COMPANY_NAME", "000-000000-00000", LocalDate.now(),
                "TEST_PAYMENT_PW", member);
        memberRepository = mock(MemberRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        memberService = new MemberService(memberRepository, paymentRepository, passwordEncoder);
    }

    @Test
    @DisplayName("DB에 존재하지 않는 Member 의 id를 조회하면, BusinessException 이 발생한다.")
    void getMemberByIdThrowsBusinessExceptionTest() {
        //given
        long ID_NOT_IN_DB = 0;
        when(memberRepository.findById(ID_NOT_IN_DB)).thenReturn(Optional.empty());
        //when & then
        assertThrows(BusinessException.class, () -> memberService.getMemberById(ID_NOT_IN_DB));
    }

    @Test
    @DisplayName("DB에 존재하는 Member 의 id를 조회하면, null 이 아닌 Member 인스턴스를 반환한다.")
    void getMemberByIdSuccessTest() {
        //given
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        //when
        Member memberById = memberService.getMemberById(anyLong());
        //then
        assertNotNull(memberById);
    }

    @Test
    @DisplayName("DB에 존재하지 않는 Payment 의 id를 조회하면, BusinessException 이 발생한다.")
    void getPaymentByIdThrowsBusinessExceptionTest() {
        //given
        long ID_NOT_IN_DB = 0;
        when(paymentRepository.findById(ID_NOT_IN_DB)).thenReturn(Optional.empty());
        //when & then
        assertThrows(BusinessException.class, () -> memberService.getPaymentById(ID_NOT_IN_DB));
    }

    @Test
    @DisplayName("DB에 존재하는 Payment 의 id를 조회하면, null 이 아닌 Payment 인스턴스를 반환한다.")
    void getPaymentByIdSuccessTest() {
        //given
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));
        //when
        Payment paymentById = memberService.getPaymentById(anyLong());
        //then
        assertNotNull(paymentById);
    }

    @Test
    @DisplayName("로그인 된 사용자가 자신의 정보를 조회하면 MemberInfoResponse 를 반환한다.")
    void inquireMyAccountInfoSuccessTest() {
        //given
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        //when
        MemberInfoResponse memberInfoResponse = memberService.inquireMyAccountInfo(anyLong());
        //then
        assertAll(
                () -> assertEquals(memberInfoResponse.getLoginId(), member.getLoginId()),
                () -> assertEquals(memberInfoResponse.getName(), member.getName()),
                () -> assertEquals(memberInfoResponse.getEmail(), member.getEmail()),
                () -> assertEquals(memberInfoResponse.getPhoneNumber(), member.getPhoneNumber()),
                () -> assertEquals(memberInfoResponse.getImageUrl(), member.getImageUrl()),
                () -> assertEquals(memberInfoResponse.getPoints(), member.getPoints())
        );
    }

    @Test
    @DisplayName("사용자가 자신의 결제수단 외의 다른 결제수단을 조회하면 BusinessException 이 발생한다.")
    void inquireMyPaymentInfoThrowsBusinessExceptionTest() {
        //given
        long OWNER_MEMBER_ID = 1L;
        long ANY_MEMBER_ID = 2L;
        payment = mock(Payment.class);
        Member mockMember = mock(Member.class);

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));
        when(payment.getMember()).thenReturn(mockMember);
        when(mockMember.getId()).thenReturn(OWNER_MEMBER_ID);

        //when & then
        assertThrows(BusinessException.class, () -> memberService.inquireMyPaymentInfo(ANY_MEMBER_ID, payment.getMember().getId()));
    }

    @Test
    @DisplayName("사용자가 자신의 결제수단을 조회하면 PaymentResponse 인스턴스를 반환한다.")
    void inquireMyPaymentInfoSuccessTest() {
        //given
        long MEMBER_ID = 1L;
        long PAYMENT_ID = 1L;
        Member mockMember = mock(Member.class);
        payment = mock(Payment.class);

        when(paymentRepository.findById(PAYMENT_ID)).thenReturn(Optional.of(payment));
        when(payment.getMember()).thenReturn(mockMember);
        when(mockMember.getId()).thenReturn(MEMBER_ID);

        //when
        PaymentResponse paymentResponse = memberService.inquireMyPaymentInfo(MEMBER_ID, PAYMENT_ID);

        //then
        assertAll(
                () -> assertEquals(paymentResponse.getId(), payment.getId()),
                () -> assertEquals(paymentResponse.getCompanyName(), payment.getCompanyName()),
                () -> assertEquals(paymentResponse.getCardNumber(), payment.getCardNumber()),
                () -> assertEquals(paymentResponse.getValidityPeriod(), payment.getValidityPeriod())
        );
    }

    @Test
    @DisplayName("사용자가 비밀번호를 변경하기 전에 비밀번호를 확인 시, 기존의 비밀번호와 일치하지 않으면 BusinessException 이 발생한다.")
    void verifyPasswordBeforeUpdateThrowsBusinessExceptionTest() {
        //given
        String MEMBER_PASSWORD = "TEST_PASSWORD";
        String WRONG_PASSWORD = "WRONG_PASSWORD";
        member = mock(Member.class);
        when(member.getPassword()).thenReturn(passwordEncoder.encode(MEMBER_PASSWORD));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        //when & then
        assertThrows(BusinessException.class, () -> memberService.verifyPasswordBeforeUpdate(anyLong(), WRONG_PASSWORD));
    }

    @Test
    @DisplayName("사용자가 비밀번호를 변경 시, 기존 비밀번호와 일치하면 BusinessException 이 발생한다.")
    void changeMyAccountPasswordThrowsBusinessExceptionTest() {
        //given
        String MEMBER_PASSWORD = "TEST_PASSWORD";
        String NEW_INPUT_PASSWORD = MEMBER_PASSWORD;
        member = mock(Member.class);
        when(member.getPassword()).thenReturn(passwordEncoder.encode(MEMBER_PASSWORD));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        //when & then
        assertThrows(BusinessException.class, () -> memberService.changeMyAccountPassword(anyLong(), NEW_INPUT_PASSWORD));
    }
}