package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "CLUB")
public class Club extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String name;

    @Column(length = 1000)
    @Size(max = 1000)
    @NotBlank
    private String description;

    @ManyToOne
    @JoinColumn(name = "code_key")
    @NotNull
    private Code code;

    @Min(0)
    private int points;

    @Column(name = "pay_date", columnDefinition = "TINYINT")
    @Min(1)
    @Max(31)
    @NotNull
    private int payDate;

    @OneToOne
    @JoinColumn(name = "image_url_id")
    private ImageUrl imageUrl;

    @Column(name = "club_fee")
    @Min(0)
    @NotNull
    private int clubFee;


    @ManyToOne
    @JoinColumn(name = "district_id")
    @NotNull
    private District district;


}
