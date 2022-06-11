package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.common.utils.AuthUtils;
import com.project.momo.dto.member.MemberInfoResponse;
import com.project.momo.dto.member.PasswordUpdateRequest;
import com.project.momo.dto.payment.PaymentRequest;
import com.project.momo.dto.payment.PaymentResponse;
import com.project.momo.dto.payment.PaymentResponseList;
import com.project.momo.entity.Member;
import com.project.momo.entity.Payment;
import com.project.momo.repository.MemberRepository;
import com.project.momo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    private static final short MAX_PAYMENT_CNT = 3;

    @Transactional(readOnly = true)
    public Member getMemberById() {
        return memberRepository
                .findById(AuthUtils.getMemberId())
                .orElseThrow(() -> {throw new BusinessException(ErrorCode.NO_MEMBER_FOUND);});
    }

    @Transactional(readOnly = true)
    public Payment getPaymentById(long paymentId) {

        return paymentRepository
                .findById(paymentId)
                .orElseThrow(()->{throw new BusinessException(ErrorCode.NO_PAYMENT_FOUND);});
    }

    public PaymentResponse getPayment(long paymentId) {
        Member member = getMemberById();
        Payment payment = getPaymentById(paymentId);
        checkAuthForPayment(member, payment);

        return PaymentResponse.createPaymentResponse(payment);
    }

    @Transactional(readOnly = true)
    public PaymentResponseList getPaymentsList() {
        Member member = getMemberById();
        return PaymentResponseList.createPaymentResponseList(paymentRepository.findAllByMember(member));
    }

    @Transactional
    public MemberInfoResponse updateName(String name) {
        Member member = getMemberById();
        member.updateName(name);
        return MemberInfoResponse.createMemberInfoResponse(member);
    }

    @Transactional
    public MemberInfoResponse updateEmail(String email) {
        Member member = getMemberById();
        member.updateEmail(email);
        return MemberInfoResponse.createMemberInfoResponse(member);
    }

    @Transactional
    public MemberInfoResponse updatePhoneNumber(String phoneNumber) {
        Member member = getMemberById();
        member.updatePhoneNumber(phoneNumber);
        return MemberInfoResponse.createMemberInfoResponse(member);
    }

    @Transactional
    public MemberInfoResponse updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        Member member = getMemberById();

        checkPasswordMatch(member, passwordUpdateRequest.getCurPassword());
        checkPasswordDuplicate(member, passwordUpdateRequest.getNewPassword());
        member.updatePassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));

        return MemberInfoResponse.createMemberInfoResponse(member);
    }

    public MemberInfoResponse updatePayment(long paymentId, PaymentRequest paymentRequest) {
        Member member = getMemberById();
        Payment payment = getPaymentById(paymentId);
        payment.updatePayment(paymentRequest.getCompanyName(), paymentRequest.getCardNumber(), paymentRequest.getValidityPeriod());

        return MemberInfoResponse.createMemberInfoResponse(member);
    }

    @Transactional
    public MemberInfoResponse addPayment(PaymentRequest paymentRequest) {
        Member member = getMemberById();
        checkPaymentCnt(member);

        Payment payment = paymentRequest.toPayment(member);
        member.increasePaymentCnt();

        paymentRepository.save(payment);
        return MemberInfoResponse.createMemberInfoResponse(member);
    }

    @Transactional
    public MemberInfoResponse deletePayment(long paymentId) {
        Payment payment = getPaymentById(paymentId);
        Member member = getMemberById();
        checkAuthForPayment(member, payment);

        paymentRepository.deleteById(paymentId);
        member.decreasePaymentCnt();

        return MemberInfoResponse.createMemberInfoResponse(member);
    }

    @Transactional
    public void deleteAccount() {
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
        if(member.getId()!=payment.getMember().getId())
            throw new BusinessException(ErrorCode.NO_AUTHORIZATION);
    }
}
