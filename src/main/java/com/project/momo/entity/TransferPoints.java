package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSFER_POINTS")
public class TransferPoints extends BaseEntity {

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

}
