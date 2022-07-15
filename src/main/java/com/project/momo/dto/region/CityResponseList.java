package com.project.momo.dto.region;

import com.project.momo.entity.City;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CityResponseList {
    private List<CityResponse> cityResponseList = new ArrayList<>();

    public CityResponseList(List<City> cities) {
        cities.stream().map(CityResponse::new).forEach(cityResponseList::add);
    }
}
