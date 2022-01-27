package com.project.momo.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "MEMBER")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "login_id", length = 45)
    private String loginId;

    private String password;

    @Column(length = 45)
    private String name;

    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    private int points;

    @OneToOne
    @JoinColumn(name = "image_url_id")
    private ImageUrl imageUrl;

    @Column(name = "payment_cnt", columnDefinition = "TINYINT")
    private int paymentCnt;


}
