package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Entity
@Table(name = "CLUB")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Size(max = 45)
    @NotBlank
    private String name;

    @Size(max = 1000)
    @NotBlank
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Min(0)
    private long points;

    @Column(name = "pay_date", columnDefinition = "TINYINT")
    @Min(1)
    @Max(31)
    private Integer payDate;

    @Column(name = "image_url")
    @Size(max = 500)
    private String imageUrl;

    @Column(name = "club_fee")
    @Min(0)
    private long clubFee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_id")
    private District district;

    public static Club createClub(String name, String description, Category category, String imageUrl, District district, String memberLoginId) {
        Club club = new Club();
        club.name = name;
        club.description = description;
        club.category = category;
        club.imageUrl = imageUrl;
        club.district = district;
        club.createdBy = memberLoginId;
        return club;
    }
}
