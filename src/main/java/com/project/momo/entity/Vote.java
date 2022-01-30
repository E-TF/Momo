package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "VOTE")
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String title;

    @Column(name = "start_at")
    @NotNull
    private LocalDateTime startAt;

    @Column(name = "finish_at")
    @NotNull
    private LocalDateTime finishAt;

    @ManyToOne
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

}
