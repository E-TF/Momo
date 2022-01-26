package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSFER_POINTS")
public class TransferPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sending_member_id")
    private Member sendMember;

    @ManyToOne
    @JoinColumn(name = "receiving_member_id")
    private Member receiveMember;

    private int amount;

    @Column(name = "transfer_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime transferAt;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20)
    private String createdBy;
}
