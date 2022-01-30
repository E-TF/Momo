package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DISTRICT_OF_INTEREST")
public class DistrictOfInterest extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @NotNull
    private District district;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;
}
