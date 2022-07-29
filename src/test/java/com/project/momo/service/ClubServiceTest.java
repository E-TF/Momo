package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.club.ClubRegisterRequest;
import com.project.momo.repository.ClubRepository;
import com.project.momo.repository.ConsistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClubServiceTest {
    private ClubRepository clubRepository;
    private ConsistRepository consistRepository;
    private ClubService clubService;
    private MemberService memberService;
    private CategoryService categoryService;
    private RegionService regionService;

    @BeforeEach
    void beforeEach() {
        clubRepository = mock(ClubRepository.class);
        consistRepository = mock(ConsistRepository.class);
        memberService = mock(MemberService.class);
        categoryService = mock(CategoryService.class);
        regionService = mock(RegionService.class);
        clubService = new ClubService(clubRepository, consistRepository, memberService, categoryService, regionService);
    }

    @Test
    @DisplayName("이미 존재하는 모임 이름으로 모임을 생성하면 에러를 던짐")
    void registerNewClubFailByDuplicatedClubName() {
        //given
        String DUPLICATED_CLUB_NAME = "DUPLICATED_CLUB_NAME";
        ClubRegisterRequest clubRegisterRequest = new ClubRegisterRequest(
                DUPLICATED_CLUB_NAME, "DESCRIPTION",
                0L, 0L, null);
        when(clubRepository.existsByName(DUPLICATED_CLUB_NAME)).thenReturn(true);

        //when & then
        try {
            clubService.registerNewClub(clubRegisterRequest, 0L);
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.DUPLICATED_CLUB_NAME);
            return;
        }
        fail();
    }
}
