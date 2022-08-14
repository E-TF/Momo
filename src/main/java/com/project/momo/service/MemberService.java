package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.common.lock.DistributedLock;
import com.project.momo.common.lock.DistributedLockPrefix;
import com.project.momo.common.lock.LockName;
import com.project.momo.common.utils.PasswordManager;
import com.project.momo.dto.member.MemberInfoResponse;
import com.project.momo.dto.payment.PaymentRequest;
import com.project.momo.dto.payment.PaymentResponse;
import com.project.momo.entity.Member;
import com.project.momo.entity.Payment;
import com.project.momo.repository.MemberRepository;
import com.project.momo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordManager passwordManager;
    @Value("${service.member.max-payment-count}")
    private short MAX_PAYMENT_CNT;

    @Transactional(readOnly = true)
    public Member getMemberById(long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.MEMBER_NOT_FOUND)
                );
    }

    @Transactional(readOnly = true)
    public Payment getPaymentById(long paymentId) {
        return paymentRepository
                .findById(paymentId)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.PAYMENT_NOT_FOUND)
                );
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse inquireMyAccountInfo(long memberId) {
        final Member member = getMemberById(memberId);
        return new MemberInfoResponse(member, paymentRepository.countByMemberId(memberId));
    }

    @Transactional(readOnly = true)
    public PaymentResponse inquireMyPaymentInfo(long memberId, long paymentId) {
        final Payment payment = getPaymentById(paymentId);
        checkAuthForPayment(memberId, payment);
        return new PaymentResponse(payment);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> inquireAllMyPaymentsInfo(long memberId) {
        return paymentRepository
                .findAllByMemberId(getMemberById(memberId).getId())
                .stream().map(PaymentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeMyAccountName(long memberId, String name) {
        final Member member = getMemberById(memberId);
        member.updateName(name);
    }

    @Transactional
    public void changeMyAccountEmail(long memberId, String email) {
        final Member member = getMemberById(memberId);
        member.updateEmail(email);
    }

    @Transactional
    public void changeMyAccountPhoneNumber(long memberId, String phoneNumber) {
        final Member member = getMemberById(memberId);
        member.updatePhoneNumber(phoneNumber);
    }

    @Transactional
    public void changeMyAccountPassword(long memberId, String rawInputPassword) {
        final Member member = getMemberById(memberId);
        checkNewPasswordDuplicateWithPreviousPassword(member.getPassword(), rawInputPassword);
        member.updatePassword(passwordManager.encodePassword(rawInputPassword));
    }

    @Transactional
    public void changeMyAccountPayment(long memberId, long paymentId, PaymentRequest paymentRequest) {
        final Payment payment = getPaymentById(paymentId);
        checkAuthForPayment(memberId, payment);
        payment.updatePayment(paymentRequest.getCompanyName(), paymentRequest.getCardNumber(), paymentRequest.getValidityPeriod());
    }

    @DistributedLock(prefix = DistributedLockPrefix.MEMBER_ID)
    @Transactional
    public void registerNewPayment(@LockName long memberId, PaymentRequest paymentRequest) {
        checkPaymentCntOverMaxLimit(memberId);
        final Member member = getMemberById(memberId);
        final Payment payment = paymentRequest.toPayment(member);
        paymentRepository.save(payment);
    }

    @Transactional
    public void removeMyPayment(long memberId, long paymentId) {
        final Payment payment = getPaymentById(paymentId);
        checkAuthForPayment(memberId, payment);
        paymentRepository.deleteById(paymentId);
    }

    @Transactional(readOnly = true)
    public void verifyPasswordBeforeUpdate(long memberId, String password) {
        final Member member = getMemberById(memberId);
        checkPasswordMatch(member.getPassword(), password);
    }

    @Transactional
    public void withdraw(long memberId) {
        final Member member = getMemberById(memberId);
        paymentRepository.deleteAllByMember(member);
        memberRepository.deleteById(member.getId());
    }

    @Transactional(readOnly = true)
    public void checkPaymentCntOverMaxLimit(long memberId) {
        if (paymentRepository.countByMemberId(memberId) >= MAX_PAYMENT_CNT)
            throw new BusinessException(ErrorCode.EXCEED_PAYMENT_CNT_LIMIT);
    }

    private void checkPasswordMatch(String encodedMemberPassword, String rawInputPassword) {
        if (!passwordManager.matches(rawInputPassword, encodedMemberPassword))
            throw new BusinessException(ErrorCode.WRONG_PASSWORD);
    }

    private void checkNewPasswordDuplicateWithPreviousPassword(String encodedMemberPassword, String rawInputPassword) {
        if (passwordManager.matches(rawInputPassword, encodedMemberPassword)) {
            throw new BusinessException(ErrorCode.DUPLICATED_PASSWORD);
        }
    }

    private void checkAuthForPayment(long memberId, Payment payment) {
        if (!payment.getMember().getId().equals(memberId))
            throw new BusinessException(ErrorCode.NO_AUTHORIZATION);
    }
}