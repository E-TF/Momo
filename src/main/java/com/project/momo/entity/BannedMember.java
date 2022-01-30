package com.project.momo.entity;

import javax.persistence.*;

@Entity
@Table(name = "BANNED_MEMBER")
public class BannedMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

}
