package com.project.momo.entity;

import javax.persistence.*;

@Entity
@Table(name = "CLUB")
public class Club extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 45)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "code_key")
    private Code code;

    private int points;

    @Column(name = "pay_date", columnDefinition = "TINYINT")
    private int payDate;

    @OneToOne
    @JoinColumn(name = "image_url_id")
    private ImageUrl imageUrl;

    @Column(name = "club_fee")
    private int clubFee;


    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;


}
