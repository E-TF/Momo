package com.project.momo.controller;

import com.project.momo.common.utils.AuthUtils;
import com.project.momo.dto.member.*;
import com.project.momo.dto.payment.PaymentRequest;
import com.project.momo.dto.payment.PaymentResponse;
import com.project.momo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/my-simple-info")
    public ResponseEntity<MemberSimpleInfoResponse> inquireMySimpleInfo() {
        return ResponseEntity.ok().body(memberService.inquireMySimpleInfo(getCurrentMemberId()));
    }

    @GetMapping("/my-info")
    public ResponseEntity<MemberInfoResponse> inquireMyAccountInfo() {
        return ResponseEntity.ok().body(memberService.inquireMyAccountInfo(getCurrentMemberId()));
    }

    @GetMapping("/payments/check")
    public ResponseEntity checkPaymentCnt() {
        memberService.checkPaymentCntOverMaxLimit(getCurrentMemberId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<PaymentResponse> inquireMyPaymentInfo(@PathVariable final long paymentId) {
        return ResponseEntity.ok().body(memberService.inquireMyPaymentInfo(getCurrentMemberId(), paymentId));
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentResponse>> inquireAllMyPaymentsInfo() {
        return ResponseEntity.ok().body(memberService.inquireAllMyPaymentsInfo(getCurrentMemberId()));
    }

    @PutMapping("/password/verify")
    public ResponseEntity verifyPasswordToUpdatePassword(
            @RequestBody @Valid final MemberPasswordVerifyRequest memberPasswordVerifyRequest
    ) {
        memberService.verifyPasswordBeforeUpdate(getCurrentMemberId(), memberPasswordVerifyRequest.getPassword());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/name")
    public ResponseEntity<MemberInfoResponse> changeMyAccountName(
            @RequestBody @Valid final MemberNameUpdateRequest memberNameUpdateRequest
    ) {
        memberService.changeMyAccountName(getCurrentMemberId(), memberNameUpdateRequest.getName());
        return ResponseEntity.ok(memberService.inquireMyAccountInfo(getCurrentMemberId()));
    }

    @PatchMapping("/email")
    public ResponseEntity<MemberInfoResponse> changeMyAccountEmail(
            @RequestBody @Valid final MemberEmailUpdateRequest memberEmailUpdateRequest
    ) {
        memberService.changeMyAccountEmail(getCurrentMemberId(), memberEmailUpdateRequest.getEmail());
        return ResponseEntity.ok(memberService.inquireMyAccountInfo(getCurrentMemberId()));
    }

    @PatchMapping("/password")
    public ResponseEntity<MemberInfoResponse> changeMyAccountPassword(
            @RequestBody @Valid final MemberPasswordUpdateRequest memberPasswordUpdateRequest
    ) {
        memberService.changeMyAccountPassword(getCurrentMemberId(), memberPasswordUpdateRequest.getPassword());
        return ResponseEntity.ok(memberService.inquireMyAccountInfo(getCurrentMemberId()));
    }

    @PatchMapping("/phone-number")
    public ResponseEntity<MemberInfoResponse> changeMyAccountPhoneNumber(
            @RequestBody @Valid final MemberPhoneNumberUpdateRequest memberPhoneNumberUpdateRequest
    ) {
        memberService.changeMyAccountPhoneNumber(getCurrentMemberId(), memberPhoneNumberUpdateRequest.getPhoneNumber());
        return ResponseEntity.ok(memberService.inquireMyAccountInfo(getCurrentMemberId()));
    }

    @PutMapping("/payment/{paymentId}")
    public ResponseEntity<MemberInfoResponse> changeMyAccountPayment(
            @PathVariable final long paymentId, @RequestBody @Valid final PaymentRequest paymentRequest
    ) {
        memberService.changeMyAccountPayment(getCurrentMemberId(), paymentId, paymentRequest);
        return ResponseEntity.ok(memberService.inquireMyAccountInfo(getCurrentMemberId()));
    }

    @PostMapping("/payments")
    public ResponseEntity<MemberInfoResponse> registerNewPayment(
            @RequestBody @Valid final PaymentRequest paymentRequest
    ) {
        long memberId = getCurrentMemberId();
        memberService.registerNewPayment(memberId, paymentRequest);
        return ResponseEntity.ok(memberService.inquireMyAccountInfo(memberId));
    }

    @DeleteMapping("/payment/{paymentId}")
    public ResponseEntity<MemberInfoResponse> removeMyPayment(@PathVariable final long paymentId) {
        long memberId = getCurrentMemberId();
        memberService.removeMyPayment(memberId, paymentId);
        return ResponseEntity.ok(memberService.inquireMyAccountInfo(memberId));
    }

    @DeleteMapping
    public ResponseEntity withdraw() {
        memberService.withdraw(getCurrentMemberId());
        return ResponseEntity.noContent().build();
    }

    private long getCurrentMemberId() {
        return AuthUtils.getMemberId();
    }
}
