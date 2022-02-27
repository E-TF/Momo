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

    @Column(name = "login_id", length = 45, unique = true)
    @Size(min = 3, max = 45)
    private String loginId;

    @Size(max = 255)
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
    private String phoneNumber;

    @Min(0)
    private long points;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_url_id")
    private ImageUrl imageUrl;

    @Column(name = "payment_cnt", columnDefinition = "TINYINT")
    @Min(0)
    @Max(3)
    private int paymentCnt;

    @Column(name = "oauth_type")
    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    @Column(name = "oauth_id")
    private long oauthId;

    public void setImageUrl(ImageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void update(String name, String email, ImageUrl imageUrl) {
        this.name = name;
        this.email = email;
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
