package com.project.momo.controller;

import com.project.momo.common.annotation.MemberEmail;
import com.project.momo.common.annotation.MemberName;
import com.project.momo.common.utils.AuthUtils;
import com.project.momo.dto.member.MemberInfoResponse;
import com.project.momo.dto.member.PasswordUpdateRequest;
import com.project.momo.dto.payment.PaymentRequest;
import com.project.momo.entity.Payment;
import com.project.momo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/my-info")
    public ResponseEntity<MemberInfoResponse> memberInfo() {
        return ResponseEntity.ok().body(memberService.getMemberInfo());
    }

    @PutMapping("/name")
    public ResponseEntity<MemberInfoResponse> updateName(@RequestParam @MemberName String name) {
        MemberInfoResponse memberInfoResponse = memberService.updateName(name);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @PutMapping("/email")
    public ResponseEntity<MemberInfoResponse> updateEmail(@RequestParam @MemberEmail String email) {
        MemberInfoResponse memberInfoResponse = memberService.updateEmail(email);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @PutMapping("/password")
    public ResponseEntity<MemberInfoResponse> updatePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        MemberInfoResponse memberInfoResponse = memberService.updatePassword(passwordUpdateRequest);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @GetMapping("/payments/check")
    public ResponseEntity<Boolean> checkPaymentCnt() {
        memberService.checkPayment();
        return ResponseEntity.ok().body(true);
    }

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getPayments() {
        List<Payment> payments = memberService.getPaymentsList();
        return ResponseEntity.ok().body(payments);  //TODO 여기를 boolean 타입으로 반환해도 괜찮을까?
    }

    @PostMapping("/payments")
    public ResponseEntity<MemberInfoResponse> addPayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        MemberInfoResponse memberInfoResponse = memberService.addPayment(paymentRequest);
        return ResponseEntity.ok(memberInfoResponse);
    }

//    @DeleteMapping("/password/{}")
}
