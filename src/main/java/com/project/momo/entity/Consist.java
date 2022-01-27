package com.project.momo.entity;

import javax.persistence.*;

@Entity
@Table(name = "CONSIST")
public class Consist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "code_key")
    private Code code;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

}
