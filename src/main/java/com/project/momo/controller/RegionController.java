package com.project.momo.controller;

import com.project.momo.dto.region.CityResponseList;
import com.project.momo.dto.region.DistrictResponseList;
import com.project.momo.dto.region.StateResponseList;
import com.project.momo.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/region")
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/cities")
    public ResponseEntity<CityResponseList> inquireAllCities() {
        return ResponseEntity.ok(regionService.inquireAllCity());
    }

    @GetMapping("/states")
    public ResponseEntity<StateResponseList> inquireAllStatesWithCity(@RequestParam long cityId) {
        return ResponseEntity.ok(regionService.inquireAllStatesWithCity(cityId));
    }

    @GetMapping("/districts")
    public ResponseEntity<DistrictResponseList> inquireAllDistrictsWithState(@RequestParam long stateId) {
        return ResponseEntity.ok(regionService.inquireAllDistrictsWithState(stateId));
    }
}
