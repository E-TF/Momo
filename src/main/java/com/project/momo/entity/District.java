package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DISTRICT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class District {

    @Id
    private Long id;

    @Column
    @Size(max = 10)
    @NotBlank
    private String state;

    @Column
    @Size(max = 10)
    @NotBlank
    private String city;

    @Column
    @Size(max = 15)
    @NotBlank
    private String district;

}
