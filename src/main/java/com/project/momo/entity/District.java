package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@Table(name = "DISTRICT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class District {
    @Id
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;
}
