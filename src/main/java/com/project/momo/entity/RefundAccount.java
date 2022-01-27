package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REFUND_ACCOUNT")
public class RefundAccount extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bank_name", length = 20)
    private String bankName;

    @Column(name = "account_number", length = 20)
    private String accountNumber;

    @Column(length = 45)
    private String holder;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
