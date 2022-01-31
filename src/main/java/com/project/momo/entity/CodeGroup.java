package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CODE_GROUP")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeGroup {

    @Id
    @Column(name = "group_key", length = 20)
    @Size(max = 20)
    @NotNull
    private String groupKey;

    @Column(length = 20)
    @Size(max = 20)
    @NotBlank
    private String name;

}
