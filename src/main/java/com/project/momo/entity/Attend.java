package com.project.momo.entity;

import javax.persistence.*;

@Entity
@Table(name = "ATTEND")
public class Attend extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "gathering_id")
    private Gathering gathering;

}
