package com.project.momo.dto.region;

import com.project.momo.entity.City;
import lombok.Getter;

@Getter
public class CityResponse {
    private long id;
    private String name;

    public CityResponse(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }
}
