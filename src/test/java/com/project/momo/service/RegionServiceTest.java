package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.region.CityResponse;
import com.project.momo.dto.region.DistrictResponse;
import com.project.momo.dto.region.StateResponse;
import com.project.momo.entity.City;
import com.project.momo.entity.District;
import com.project.momo.entity.State;
import com.project.momo.repository.CityRepository;
import com.project.momo.repository.DistrictRepository;
import com.project.momo.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegionServiceTest {

    private StateRepository stateRepository;
    private CityRepository cityRepository;
    private DistrictRepository districtRepository;
    private RegionService regionService;

    private final long STATE_ID = 0;
    private final String STATE_NAME = "testStateName";
    private final State state = new State(STATE_ID, STATE_NAME);
    private final long CITY_ID = 1;
    private final String CITY_NAME = "testCityName";
    private final City city = new City(CITY_ID, CITY_NAME, state);
    private final long DISTRICT_ID = 2;
    private final String DISTRICT_NAME = "testDistrictName";
    private final District district = new District(DISTRICT_ID, DISTRICT_NAME, city);

    @BeforeEach
    void beforeEach() {
        stateRepository = mock(StateRepository.class);
        cityRepository = mock(CityRepository.class);
        districtRepository = mock(DistrictRepository.class);
        regionService = new RegionService(cityRepository, stateRepository, districtRepository);
    }

    @Test
    @DisplayName("전체 STATE 를 조회하면 NULL 이 아닌 StateResponse 의 List 를 반환한다.")
    void inquireAllStatesSuccessTest() {
        //given
        when(stateRepository.findAll()).thenReturn(List.of(state));

        //when
        List<StateResponse> stateResponses = regionService.inquireAllStates();

        //then
        assertEquals(stateResponses.size(), 1);
        StateResponse stateResponse = stateResponses.get(0);
        assertEquals(stateResponse.getId(), STATE_ID);
        assertEquals(stateResponse.getName(), STATE_NAME);
    }

    @Test
    @DisplayName("DB에 존재하지 않는 STATE 의 id를 조회하면 STATE_NOT_FOUND(BusinessException) 이 발생한다.")
    void inquireAllCitiesWithStateFailTest() {
        //given
        final long STATE_ID_NOT_IN_DB = -1;
        when(cityRepository.existsById(STATE_ID_NOT_IN_DB)).thenReturn(false);

        //when & then
        try {
            regionService.inquireAllCitiesWithState(STATE_ID_NOT_IN_DB);
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.STATE_NOT_FOUND);
            return;
        }
        fail();
    }

    @Test
    @DisplayName("DB에 존재하는 STATE 의 id를 조회하면 NULL 이 아닌 StateResponse 의 List 를 반환한다.")
    void inquireAllCitiesWithStateSuccessTest() {
        //given
        when(stateRepository.existsById(anyLong())).thenReturn(true);
        when(cityRepository.findAllByStateId(anyLong())).thenReturn(List.of(city));

        //when
        List<CityResponse> cityResponses = regionService.inquireAllCitiesWithState(STATE_ID);

        //then
        assertEquals(cityResponses.size(), 1);
        CityResponse cityResponse = cityResponses.get(0);
        assertEquals(cityResponse.getId(), CITY_ID);
        assertEquals(cityResponse.getName(), CITY_NAME);
    }

    @Test
    @DisplayName("DB에 존재하지 않는 CITY 의 id를 조회하면 CITY_NOT_FOUND(BusinessException)이 발생한다.")
    void inquireAllDistrictsWithCityFailTest() {
        //given
        final long CITY_ID_NOT_IN_DB = -1;
        when(districtRepository.existsById(CITY_ID_NOT_IN_DB)).thenReturn(false);

        //when & then
        try {
            regionService.inquireAllDistrictsWithCity(CITY_ID_NOT_IN_DB);
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.CITY_NOT_FOUND);
            return;
        }
        fail();
    }

    @Test
    @DisplayName("DB에 존재하는 CITY 의 id를 조회하면 NULL 이 아닌 DistrictResponse 의 List 를 반환한다.")
    void inquireAllDistrictsWithCitySuccessTest() {
        //given
        when(districtRepository.findAllByCityId(anyLong())).thenReturn(List.of(district));
        when(cityRepository.existsById(anyLong())).thenReturn(true);

        //when
        List<DistrictResponse> districtResponses = regionService.inquireAllDistrictsWithCity(CITY_ID);

        //then
        assertEquals(districtResponses.size(), 1);
        DistrictResponse districtResponse = districtResponses.get(0);
        assertEquals(districtResponse.getId(), DISTRICT_ID);
        assertEquals(districtResponse.getName(), DISTRICT_NAME);
    }

    @Test
    @DisplayName("DB에 존재하지 않는 DISTRICT 의 id를 조회하면 DISTRICT_NOT_FOUND(BusinessException)이 발생한다.")
    void getDistrictByIdFailTest() {
        //given
        final long DISTRICT_ID_NOT_IN_DB = -1;
        when(districtRepository.findById(DISTRICT_ID_NOT_IN_DB)).thenReturn(Optional.empty());
        //when & then
        try {
            regionService.getDistrictById(DISTRICT_ID_NOT_IN_DB);
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.DISTRICT_NOT_FOUND);
            return;
        }
        fail();
    }

    @Test
    @DisplayName("DB에 존재하는 DISTRICT 의 id를 조회하면 null 이 아닌 District 인스턴스가 반환한다.")
    void getDistrictByIdSuccessTest() {
        //given
        when(districtRepository.findById(DISTRICT_ID)).thenReturn(Optional.of(district));

        //when
        District districtById = regionService.getDistrictById(DISTRICT_ID);

        //then
        assertNotNull(districtById);
        assertEquals(districtById.getId(), district.getId());
        assertEquals(districtById.getName(), district.getName());
        assertEquals(districtById.getCity(), district.getCity());
    }
}