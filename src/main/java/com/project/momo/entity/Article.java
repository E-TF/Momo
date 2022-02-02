package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ARTICLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String title;

    @Lob
    @NotBlank
    private String content;

    @Column(length = 20)
    @Size(max = 20)
    @NotBlank
    private String category;

    @Column(name = "view_cnt")
    @Min(0)
    private long viewCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
