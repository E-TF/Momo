package com.project.momo.dto.region;

import com.project.momo.entity.District;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DistrictResponseList {
    private List<DistrictResponse> districtResponseList = new ArrayList<>();

    public DistrictResponseList(List<District> districts) {
        districts.stream().map(DistrictResponse::new).forEach(districtResponseList::add);
    }
}
