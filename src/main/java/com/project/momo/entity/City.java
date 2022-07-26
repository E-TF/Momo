package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "CITY")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class City {
    @Id
    Long id;

    @Column
    String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    State state;
}
