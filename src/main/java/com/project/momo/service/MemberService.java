package com.project.momo.service;

import com.project.momo.common.utils.AuthUtils;
import com.project.momo.dto.member.MemberInfoResponse;
import com.project.momo.dto.member.PasswordUpdateRequest;
import com.project.momo.dto.payment.PaymentRequest;
import com.project.momo.entity.Member;
import com.project.momo.entity.Payment;
import com.project.momo.repository.MemberRepository;
import com.project.momo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    private final short MAX_PAYMENT_CNT = 3;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo() {
        Optional<Member> member = memberRepository.findById(AuthUtils.getMemberId());
        if (member.isEmpty()) {
            throw new RuntimeException(); //TODO 예외 추가하기
        }

        return new MemberInfoResponse(member.get());
    }

    @Transactional
    public MemberInfoResponse updateName(String name) {
        Optional<Member> member = memberRepository.findById(AuthUtils.getMemberId());
        if (member.isEmpty()) {
            throw new RuntimeException();
        }
        member.get().setName(name);

        return new MemberInfoResponse(member.get());
    }

    public MemberInfoResponse updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        Optional<Member> member = memberRepository.findById(AuthUtils.getMemberId());
        if (member.isEmpty()) {
            throw new RuntimeException();//TODO 멤버가 조회되지 않는 경우
        }
        checkPassword(passwordUpdateRequest, member.get().getPassword());
        member.get().setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        return new MemberInfoResponse(member.get());
    }

    private void checkPassword(PasswordUpdateRequest passwordUpdateRequest, String storedPassword) {
        if (!passwordEncoder.encode(passwordUpdateRequest.getCurPassword()).equals(storedPassword)) {
            throw new RuntimeException();//TODO 비밀번호 변경시 입력한 현재 비밀번호가 일치하지 않는 경우
        }

        if (passwordUpdateRequest.getNewPassword().equals(passwordUpdateRequest.getCurPassword())) {
            throw new RuntimeException();//TODO 새로 입력한 비밀번호가 기존 비밀번호와 동일한 경우
        }
    }

    public MemberInfoResponse addPayment(PaymentRequest paymentRequest) {
        Optional<Member> member = memberRepository.findById(AuthUtils.getMemberId());
        if (member.isEmpty()) {
            throw new RuntimeException();
        }
        checkPayment();
        Payment payment = Payment.createPayment(paymentRequest.getCompanyName(),
                paymentRequest.getCardNumber(),
                paymentRequest.getValidityPeriod(),
                paymentRequest.getPassword(),
                member.get()
        );
        member.get().increasePaymentCnt();

        paymentRepository.save(payment);
        return new MemberInfoResponse(member.get());
    }

    public MemberInfoResponse updateEmail(String email) {
        Optional<Member> member = memberRepository.findById(AuthUtils.getMemberId());
        if (member.isEmpty()) {
            throw new RuntimeException();
        }
        member.get().setEmail(email);

        return new MemberInfoResponse(member.get());
    }

    public void checkPayment() {
        int paymentCnt = memberRepository.findById(AuthUtils.getMemberId()).get().getPaymentCnt();
        if (paymentCnt >= MAX_PAYMENT_CNT) {
            throw new RuntimeException(); //TODO 결제 수단이 3개 초과인 경우 에러
        }
    }

    public List<Payment> getPaymentsList() {
        Optional<Member> member = memberRepository.findById(AuthUtils.getMemberId());
        if (member.isEmpty()) {
            throw new RuntimeException();
        }
        return paymentRepository.findByMember(member.get());
    }
}
