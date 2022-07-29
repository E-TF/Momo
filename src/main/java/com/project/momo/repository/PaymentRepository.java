package com.project.momo.repository;

import com.project.momo.entity.Member;
import com.project.momo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findById(long id);

    List<Payment> findAllByMemberId(Long id);

    int countByMemberId(long memberId);

    void deleteAllByMember(Member member);
}
