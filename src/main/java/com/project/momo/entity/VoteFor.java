package com.project.momo.entity;

import javax.persistence.*;

@Entity
@Table(name = "VOTE_FOR")
public class VoteFor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "vote_option_id")
    private VoteOption voteOption;
}
