package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "IMAGE_URL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUrl extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "image_url", length = 500)
    @Size(max = 500)
    @NotNull
    private String imageUrl;
}
