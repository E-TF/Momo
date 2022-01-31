package com.project.momo.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20, updatable = false)
    @Size(max = 20)
    @NotBlank
    protected String createdBy;

}
