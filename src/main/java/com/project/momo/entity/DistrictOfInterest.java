package com.project.momo.entity;

import javax.persistence.*;

@Entity
@Table(name = "DISTRICT_OF_INTEREST")
public class DistrictOfInterest extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
