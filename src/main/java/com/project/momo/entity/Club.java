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

    @Column(length = 45, unique = true)
    @Size(max = 45)
    @NotBlank
    private String name;

    @Column(length = 1000)
    @Size(max = 1000)
    @NotBlank
    private String description;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String category;

    @Min(0)
    private long points;

    @Column(name = "pay_date", columnDefinition = "TINYINT")
    @Min(1)
    @Max(31)
    private int payDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_url_id")
    private ImageUrl imageUrl;

    @Column(name = "club_fee")
    @Min(0)
    private long clubFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    @NotNull
    private District district;

}
