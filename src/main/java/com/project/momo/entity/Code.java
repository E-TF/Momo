package com.project.momo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CODE")
public class Code {

    @Id
    @Column(name = "code_key", length = 20)
    @Size(max = 20)
    @NotBlank
    private String codeKey;

    @ManyToOne
    @JoinColumn(name = "group_key")
    @NotNull
    private CodeGroup codeGroup;

    @Column(length = 20)
    @Size(max = 20)
    @NotBlank
    private String name;

}
