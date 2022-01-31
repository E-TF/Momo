package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "GATHERING")
public class Gathering extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_key")
    @NotNull
    private Code code;

    @Column(name = "gather_at")
    @NotNull
    private LocalDateTime gatherAt;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String location;

    @Column(name = "max_number", columnDefinition = "SMALLINT")
    @Min(2)
    @NotNull
    private int maxNumber;

    @Column(name = "participation_fee", columnDefinition = "MEDIUMINT")
    @NotNull
    private int participationFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;
}
