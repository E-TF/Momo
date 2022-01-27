package com.project.momo.entity;

import javax.persistence.*;

@Entity
@Table(name = "VOTE_OPTION")
public class VoteOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 45)
    private String content;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

}
