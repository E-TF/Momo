package com.project.momo.entity;

import javax.persistence.*;

@Entity
@Table(name = "CODE")
public class Code {

    @Id
    @Column(name = "code_key", length = 20)
    private String codeKey;

    @ManyToOne
    @JoinColumn(name = "group_key")
    private CodeGroup groupKey;

    @Column(length = 20)
    private String name;

}
