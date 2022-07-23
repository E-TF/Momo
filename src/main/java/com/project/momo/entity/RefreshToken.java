package com.project.momo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "REFRESH_TOKEN")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @NotEmpty
    private String token;

    public void updateToken(String token) {
        this.token = token;
    }
}
