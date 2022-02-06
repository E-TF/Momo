package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "GATHERING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gathering extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String name;

    @Column(length = 20)
    @Size(max = 20)
    @NotBlank
    private String category;

    @Column(name = "gather_at")
    @NotNull
    private LocalDateTime gatherAt;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String location;

    @Column(name = "max_number", columnDefinition = "SMALLINT")
    @Min(2)
    private int maxNumber;

    @Column(name = "participation_fee", columnDefinition = "MEDIUMINT")
    private long participationFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;
}
