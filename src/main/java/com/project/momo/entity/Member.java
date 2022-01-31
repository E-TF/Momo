package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "login_id", length = 45)
    @Size(min = 3, max = 45)
    @NotBlank
    private String loginId;

    @Size(max = 255)
    @NotNull
    private String password;

    @Column(length = 45)
    @Size(max = 45)
    @NotBlank
    private String name;

    @Size(max = 255)
    @Email
    @NotBlank
    private String email;

    @Column(name = "phone_number", length = 20)
    @Size(max = 20)
    @NotBlank
    private String phoneNumber;

    @Min(0)
    private int points;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_url_id")
    private ImageUrl imageUrl;

    @Column(name = "payment_cnt", columnDefinition = "TINYINT")
    @Min(0)
    @Max(3)
    private int paymentCnt;

    public static Member createMember(String loginId, String password, String name, String email, String phoneNumber, ImageUrl imageUrl) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.name = name;
        member.email = email;
        member.phoneNumber = phoneNumber;
        member.imageUrl = imageUrl;
        member.createdBy = "SYSTEM";
        return member;
    }
}
