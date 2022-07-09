package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.common.utils.AuthUtils;
import com.project.momo.dto.member.MemberInfoResponse;
import com.project.momo.dto.member.PasswordUpdateRequest;
import com.project.momo.dto.payment.PaymentRequest;
import com.project.momo.dto.payment.PaymentResponse;
import com.project.momo.dto.payment.PaymentListResponse;
import com.project.momo.entity.Member;
import com.project.momo.entity.Payment;
import com.project.momo.repository.MemberRepository;
import com.project.momo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${service.member.max-payment-count}")
    private static short MAX_PAYMENT_CNT;

    @Transactional(readOnly = true)
    public Member getMemberById() {
        return memberRepository
                .findById(AuthUtils.getMemberId())
                .orElseThrow(() -> {throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);});
    }

    @Transactional(readOnly = true)
    public Payment getPaymentById(long paymentId) {
        return paymentRepository
                .findById(paymentId)
                .orElseThrow(()->{throw new BusinessException(ErrorCode.NO_PAYMENT_FOUND);});
    }

    public MemberInfoResponse inquireMyAccountInfo(){
        Member member = getMemberById();
        return new MemberInfoResponse(member);
    }

    public PaymentResponse inquireMyPaymentInfo(long paymentId) {
        Member member = getMemberById();
        Payment payment = getPaymentById(paymentId);
        checkAuthForPayment(member, payment);

        return new PaymentResponse(payment);
    }

    @Transactional(readOnly = true)
    public PaymentListResponse inquireAllMyPaymentsInfo() {
        Member member = getMemberById();
        return new PaymentListResponse(paymentRepository.findAllByMemberId(member.getId()));
    }

    @Transactional
    public MemberInfoResponse changeMyAccountName(String name) {
        Member member = getMemberById();
        member.updateName(name);
        return new MemberInfoResponse(member);
    }

    @Transactional
    public MemberInfoResponse changeMyAccountEmail(String email) {
        Member member = getMemberById();
        member.updateEmail(email);
        return new MemberInfoResponse(member);
    }

    @Transactional
    public MemberInfoResponse changeMyAccountPhoneNumber(String phoneNumber) {
        Member member = getMemberById();
        member.updatePhoneNumber(phoneNumber);
        return new MemberInfoResponse(member);
    }

    @Transactional
    public MemberInfoResponse changeMyAccountPassword(PasswordUpdateRequest passwordUpdateRequest) {
        Member member = getMemberById();

        checkPasswordMatch(member, passwordUpdateRequest.getCurPassword());
        checkPasswordDuplicate(member, passwordUpdateRequest.getNewPassword());
        member.updatePassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));

        return new MemberInfoResponse(member);
    }

    public MemberInfoResponse changeMyAccountPayment(long paymentId, PaymentRequest paymentRequest) {
        Member member = getMemberById();
        Payment payment = getPaymentById(paymentId);
        payment.updatePayment(paymentRequest.getCompanyName(), paymentRequest.getCardNumber(), paymentRequest.getValidityPeriod());

        return new MemberInfoResponse(member);
    }

    @Transactional
    public MemberInfoResponse registerNewPayment(PaymentRequest paymentRequest) {
        Member member = getMemberById();
        checkPaymentCnt(member);

        Payment payment = paymentRequest.toPayment(member);
        member.increasePaymentCnt();

        paymentRepository.save(payment);
        return new MemberInfoResponse(member);
    }

    @Transactional
    public MemberInfoResponse removeMyPayment(long paymentId) {
        Payment payment = getPaymentById(paymentId);
        Member member = getMemberById();
        checkAuthForPayment(member, payment);

        paymentRepository.deleteById(paymentId);
        member.decreasePaymentCnt();

        return new MemberInfoResponse(member);
    }

    @Transactional
    public void withdraw() {
        Member member = getMemberById();
        memberRepository.deleteById(member.getId());
        paymentRepository.deleteAllByMember(member);
    }

    private void checkPasswordMatch(Member member, String password){
        if(!passwordEncoder.encode(password).equals(member.getPassword()))
            throw new BusinessException(ErrorCode.WRONG_PASSWORD);
    }

    private void checkPasswordDuplicate(Member member, String password) {
        if (passwordEncoder.encode(member.getPassword()).equals(password)) {
            throw new BusinessException(ErrorCode.DUPLICATED_PASSWORD);
        }
    }

    public void checkPaymentCnt(Member member){
        if (member.getPaymentCnt() >= MAX_PAYMENT_CNT)
            throw new BusinessException(ErrorCode.EXCEED_PAYMENT_CNT_LIMIT);
    }

    private void checkAuthForPayment(Member member, Payment payment){
        if(!member.getId().equals(payment.getMember().getId()))
            throw new BusinessException(ErrorCode.NO_AUTHORIZATION);
    }
}