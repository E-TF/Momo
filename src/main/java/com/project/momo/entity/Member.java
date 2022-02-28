package com.project.momo.entity;

import com.project.momo.security.consts.OauthType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SEQ_GENERATOR")
    private Long id;

    @Column(name = "login_id", unique = true)
    @Size(min = 3, max = 45)
    private String loginId;

    @Size(max = 255)
    private String password;

    @Column
    @Size(max = 45)
    @NotBlank
    private String name;

    @Size(max = 255)
    @Email
    @NotBlank
    private String email;

    @Column(name = "phone_number", length = 20)
    @Size(max = 20)
    private String phoneNumber;

    @Min(0)
    private long points;

    @Column(name = "image_url")
    @Size(max = 500)
    private String imageUrl;

    @Column(name = "payment_cnt", columnDefinition = "TINYINT")
    @Min(0)
    @Max(3)
    private int paymentCnt;

    @Column(name = "oauth_type")
    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    @Column(name = "oauth_id")
    private long oauthId;

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Member createMember(String loginId, String password, String name, String email, String phoneNumber) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.name = name;
        member.email = email;
        member.phoneNumber = phoneNumber;
        member.createdBy = member.getId();
        return member;
    }

    public static Member ofOauth(OauthType oauthType, long oauthId, String name, String email) {
        Member member = new Member();
        member.oauthType = oauthType;
        member.oauthId = oauthId;
        member.name = name;
        member.email = email;
        return member;
    }
}
