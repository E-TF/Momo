package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "REFUND_ACCOUNT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundAccount extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "bank_name", length = 20)
    @Size(max = 20)
    @NotBlank
    private String bankName;

    @Column(name = "account_number", length = 20)
    @Size(max = 20)
    @NotBlank
    private String accountNumber;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String holder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

}
