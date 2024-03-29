package com.project.momo.entity;

import com.project.momo.security.consts.OauthType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Entity
@Table(name = "MEMBER")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    private static final String ANONYMOUS = "anonymous";

    @Id
    @GeneratedValue
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
        member.loginId = oauthType + oauthId;
        member.oauthType = oauthType;
        member.oauthId = oauthId;
        member.name = name;
        member.email = email;
        member.phoneNumber = phoneNumber;
        member.imageUrl = imageUrl;
        member.createdBy = member.ANONYMOUS;
        return member;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
