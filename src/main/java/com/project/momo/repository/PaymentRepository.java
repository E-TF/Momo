package com.project.momo.repository;

import com.project.momo.entity.Member;
import com.project.momo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByMember(Member member);
    void deleteAllByMember(Member member);
}
