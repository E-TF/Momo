package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CLUB_POINTS_HISTORY")
public class ClubPointsHistory extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "code_key")
    private Code code;

    @Column(length = 45)
    private String description;

    private int amount;

    @Column(name = "changed_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime changedAt;

    @Column(name = "points_before")
    private int pointsBefore;

    @Column(name = "points_after")
    private int pointsAfter;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
}
