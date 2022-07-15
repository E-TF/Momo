package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "CITY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class City {
    @Id
    Long id;

    @Column
    String name;
}
