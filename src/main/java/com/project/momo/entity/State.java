package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@Table(name = "STATE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class State {

    @Id
    Long id;

    @NotBlank
    String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    City city;
}
