package com.project.momo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DISTRICT")
public class District {

    @Id
    private Long id;

    @Column(length = 10)
    @Size(max = 10)
    @NotBlank
    private String state;

    @Column(length = 10)
    @Size(max = 10)
    @NotBlank
    private String city;

    @Column(length = 15)
    @Size(max = 15)
    @NotBlank
    private String district;

}
