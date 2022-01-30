package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ADJUST_POINTS")
public class AdjustPoint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int amount;

    @Column(name = "adjust_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime adjustAt;

    @ManyToOne
    @JoinColumn(name = "memebr_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

}
