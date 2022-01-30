package com.project.momo.entity;

import javax.persistence.*;

@Entity
@Table(name = "RECOMMEND")
public class Recommend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
