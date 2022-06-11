package com.project.momo.controller;

import com.project.momo.common.annotation.MemberEmail;
import com.project.momo.common.annotation.MemberName;
import com.project.momo.common.annotation.PhoneNumber;
import com.project.momo.dto.member.MemberInfoResponse;
import com.project.momo.dto.member.PasswordUpdateRequest;
import com.project.momo.dto.payment.PaymentRequest;
import com.project.momo.dto.payment.PaymentResponse;
import com.project.momo.dto.payment.PaymentResponseList;
import com.project.momo.entity.Member;
import com.project.momo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/my-info")
    public ResponseEntity<MemberInfoResponse> memberInfo() {
        Member member = memberService.getMemberById();
        return ResponseEntity.ok().body(MemberInfoResponse.createMemberInfoResponse(member));
    }

    @GetMapping("/payments/check")
    public void checkPaymentCnt() {
        memberService.checkPaymentCnt(memberService.getMemberById());
    }

    @GetMapping("/payments")
    public ResponseEntity<PaymentResponseList> getPayments() {
        PaymentResponseList payments = memberService.getPaymentsList();
        return ResponseEntity.ok().body(payments);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable long paymentId) {
        PaymentResponse paymentResponse = memberService.getPayment(paymentId);
        return ResponseEntity.ok().body(paymentResponse);
    }

    @PatchMapping("/name")
    public ResponseEntity<MemberInfoResponse> updateName(@RequestParam @MemberName String name) {
        MemberInfoResponse memberInfoResponse = memberService.updateName(name);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @PatchMapping("/email")
    public ResponseEntity<MemberInfoResponse> updateEmail(@RequestParam @MemberEmail String email) {
        MemberInfoResponse memberInfoResponse = memberService.updateEmail(email);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @PatchMapping("/password")
    public ResponseEntity<MemberInfoResponse> updatePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        MemberInfoResponse memberInfoResponse = memberService.updatePassword(passwordUpdateRequest);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @PatchMapping("/phone-number")
    public ResponseEntity<MemberInfoResponse> updatePhoneNumber(@RequestBody @PhoneNumber String phoneNumber) {
        MemberInfoResponse memberInfoResponse = memberService.updatePhoneNumber(phoneNumber);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @PutMapping("/payment/{paymentId}")
    public ResponseEntity<MemberInfoResponse> updatePayment(@PathVariable long paymentId, @RequestBody PaymentRequest paymentRequest) {
        MemberInfoResponse memberInfoResponse = memberService.updatePayment(paymentId, paymentRequest);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @PostMapping("/payments")
    public ResponseEntity<MemberInfoResponse> addPayment(@RequestBody PaymentRequest paymentRequest) {
        MemberInfoResponse memberInfoResponse = memberService.addPayment(paymentRequest);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @DeleteMapping("/payment/{paymentId}")
    public ResponseEntity<MemberInfoResponse> deletePayment(@PathVariable long paymentId) {
        MemberInfoResponse memberInfoResponse = memberService.deletePayment(paymentId);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount() {
        memberService.deleteAccount();
        return ResponseEntity.noContent().build();
    }
}
