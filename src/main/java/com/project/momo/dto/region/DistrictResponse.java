package com.project.momo.dto.region;

import com.project.momo.entity.District;
import lombok.Getter;

@Getter
public class DistrictResponse {
    private long id;
    private String name;

    public DistrictResponse(District district) {
        this.id = district.getId();
        this.name = district.getName();
    }
}
