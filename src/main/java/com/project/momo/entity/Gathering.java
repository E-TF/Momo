package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "GATHERING")
public class Gathering extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 45)
    private String name;

    @ManyToOne
    @JoinColumn(name = "code_key")
    private Code code;

    @Column(name = "gather_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime gatherAt;

    @Column(length = 45)
    private String location;

    @Column(name = "max_number", columnDefinition = "SMALLINT")
    private int max_number;

    @Column(name = "participation_fee", columnDefinition = "MEDIUMINT")
    private int participationFee;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
}
