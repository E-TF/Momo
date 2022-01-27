package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "VOTE")
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 45)
    private String title;

    @Column(name = "start_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime startAt;

    @Column(name = "finish_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime finishAt;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

}
