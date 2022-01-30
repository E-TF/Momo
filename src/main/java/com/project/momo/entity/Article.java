package com.project.momo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ARTICLE")
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 45)
    private String title;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "code_key")
    private Code code;

    @Column(name = "view_cnt")
    private long viewCnt;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
