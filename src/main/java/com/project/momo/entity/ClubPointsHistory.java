package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CLUB_POINTS_HISTORY")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubPointsHistory extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Size(max = 20)
    @NotBlank
    private String category;

    @Size(max = 45)
    @NotBlank
    private String description;

    private long amount;

    @Column(name = "points_before")
    @Min(0)
    private long pointsBefore;

    @Column(name = "points_after")
    @Min(0)
    private long pointsAfter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;
}
