package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CHAT")
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Size(max = 255)
    @NotBlank
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

}
