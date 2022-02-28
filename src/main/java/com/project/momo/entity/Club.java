package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "CLUB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Size(max = 45)
    @NotBlank
    private String name;

    @Column
    @Size(max = 1000)
    @NotBlank
    private String description;

    @Column
    @Size(max = 45)
    @NotBlank
    private String category;

    @Min(0)
    private long points;

    @Column(name = "pay_date", columnDefinition = "TINYINT")
    @Min(1)
    @Max(31)
    private int payDate;

    @Column(name = "image_url")
    @Size(max = 500)
    private String imageUrl;

    @Column(name = "club_fee")
    @Min(0)
    private long clubFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    @NotNull
    private District district;

}
