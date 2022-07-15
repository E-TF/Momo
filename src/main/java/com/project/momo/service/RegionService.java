package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.region.CityResponseList;
import com.project.momo.dto.region.DistrictResponseList;
import com.project.momo.dto.region.StateResponseList;
import com.project.momo.entity.District;
import com.project.momo.repository.CityRepository;
import com.project.momo.repository.DistrictRepository;
import com.project.momo.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CityResponseList inquireAllCity() {
        return new CityResponseList(cityRepository.findAll());
    }

    @Transactional(readOnly = true)
    public StateResponseList inquireAllStatesWithCity(long cityId) {
        validateCity(cityId);
        return new StateResponseList(stateRepository.findAllByCityId(cityId));
    }

    @Transactional(readOnly = true)
    public DistrictResponseList inquireAllDistrictsWithState(long stateId) {
        validateState(stateId);
        return new DistrictResponseList(districtRepository.findAllByStateId(stateId));
    }

    private void validateCity(long cityId) {
        if (!cityRepository.existsById(cityId))
            throw new BusinessException(ErrorCode.CITY_NOT_FOUND);
    }

    private void validateState(long stateId) {
        if (!stateRepository.existsById(stateId)) {
            throw new BusinessException(ErrorCode.STATE_NOT_FOUND);
        }
    }
}
