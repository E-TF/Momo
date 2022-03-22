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

    private static final String ANONYMOUS = "anonymous";

    @Id
    @GeneratedValue(generator = "SEQ_GENERATOR")
    private Long id;

    @Column(name = "login_id", unique = true)
    @Size(min = 3, max = 45)
    private String loginId;

    @Size(max = 255)
    private String password;

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
    @Size(max = 20)
    private String oauthId;

    public static Member createMember(String loginId, String password, String name, String email, String phoneNumber) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.name = name;
        member.email = email;
        member.phoneNumber = phoneNumber;
        member.createdBy = member.loginId;
        return member;
    }

    public static Member createOauth(OauthType oauthType, String oauthId, String name, String email, String phoneNumber, String imageUrl) {
        Member member = new Member();
        member.oauthType = oauthType;
        member.oauthId = oauthId;
        member.name = name;
        member.email = email;
        member.phoneNumber = phoneNumber;
        member.imageUrl = imageUrl;
        member.createdBy = member.ANONYMOUS;
        return member;
    }

}
