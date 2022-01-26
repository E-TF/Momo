package com.project.momo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CODE_GROUP")
public class CodeGroup {

    @Id
    @Column(name = "group_key", length = 20)
    private String groupKey;

    @Column(length = 20)
    private String name;

    @OneToMany(mappedBy = "groupKey")
    private List<Code> codes = new ArrayList<>();

}
