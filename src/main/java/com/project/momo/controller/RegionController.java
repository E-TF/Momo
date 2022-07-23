package com.project.momo.controller;

import com.project.momo.dto.region.CityResponse;
import com.project.momo.dto.region.DistrictResponse;
import com.project.momo.dto.region.StateResponse;
import com.project.momo.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/region")
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/cities")
    public ResponseEntity<List<StateResponse>> inquireAllStates() {
        return ResponseEntity.ok(regionService.inquireAllStates());
    }

    @GetMapping("/states")
    public ResponseEntity<List<CityResponse>> inquireAllCitiesWithState(@RequestParam final long stateId) {
        return ResponseEntity.ok(regionService.inquireAllCitiesWithState(stateId));
    }

    @GetMapping("/districts")
    public ResponseEntity<List<DistrictResponse>> inquireAllDistrictsWithCity(@RequestParam final long cityId) {
        return ResponseEntity.ok(regionService.inquireAllDistrictsWithCity(cityId));
    }
}
