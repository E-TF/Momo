package com.project.momo.repository;

import com.project.momo.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final EntityManager em;

    public Long save(Payment payment) {
        em.persist(payment);
        return payment.getId();
    }
}
