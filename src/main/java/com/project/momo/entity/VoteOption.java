package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "VOTE_OPTION")
public class VoteOption extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    @NotNull
    private Vote vote;

}
