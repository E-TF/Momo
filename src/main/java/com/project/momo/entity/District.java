package com.project.momo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISTRICT")
public class District {

    @Id
    private Long id;

    @Column(length = 10)
    private String state;

    @Column(length = 10)
    private String city;

    @Column(length = 15)
    private String district;

}
