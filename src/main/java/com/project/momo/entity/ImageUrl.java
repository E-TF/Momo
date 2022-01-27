package com.project.momo.entity;

import javax.persistence.*;

@Entity
@Table(name = "IMAGE_URL")
public class ImageUrl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
}
