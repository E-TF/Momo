package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.region.CityResponse;
import com.project.momo.dto.region.DistrictResponse;
import com.project.momo.dto.region.StateResponse;
import com.project.momo.entity.District;
import com.project.momo.repository.CityRepository;
import com.project.momo.repository.DistrictRepository;
import com.project.momo.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final DistrictRepository districtRepository;

    @Transactional(readOnly = true)
    public District getDistrictById(long districtId) {
        return districtRepository
                .findById(districtId)
                .orElseThrow(()->
                        new BusinessException(ErrorCode.DISTRICT_NOT_FOUND)
                );
    }

    @Transactional(readOnly = true)
    public List<StateResponse> inquireAllStates() {
        return stateRepository
                .findAll()
                .stream()
                .map(StateResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CityResponse> inquireAllCitiesWithState(long stateId) {
        validateState(stateId);
        return cityRepository
                .findAllByStateId(stateId)
                .stream()
                .map(CityResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DistrictResponse> inquireAllDistrictsWithCity(long cityId) {
        validateCity(cityId);
        return districtRepository
                .findAllByCityId(cityId)
                .stream()
                .map(DistrictResponse::new)
                .collect(Collectors.toList());
    }

    private void validateState(long stateId) {
        if (!stateRepository.existsById(stateId)) {
            throw new BusinessException(ErrorCode.STATE_NOT_FOUND);
        }
    }

    private void validateCity(long cityId) {
        if (!cityRepository.existsById(cityId))
            throw new BusinessException(ErrorCode.CITY_NOT_FOUND);
    }
}
