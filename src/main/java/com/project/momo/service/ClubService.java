package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.club.ClubRegisterRequest;
import com.project.momo.dto.club.ClubSimpleInfoResponse;
import com.project.momo.entity.Category;
import com.project.momo.entity.Club;
import com.project.momo.entity.District;
import com.project.momo.entity.Member;
import com.project.momo.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final RegionService regionService;

    @Transactional(readOnly = true)
    public Club getClubById(long clubId) {
        return clubRepository
                .findById(clubId)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.CLUB_NOT_FOUND)
                );
    }

    @Transactional(readOnly = true)
    public Club getClubByName(String clubName) {
        return clubRepository
                .findByName(clubName)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.CLUB_NOT_FOUND)
                );
    }

    @Transactional(readOnly = true)
    public ClubSimpleInfoResponse inquireClubSimpleInfo(long clubId) {
        final Club club = getClubById(clubId);
        return new ClubSimpleInfoResponse(club);
    }

    public void registerNewClub(ClubRegisterRequest clubRegisterRequest, long memberId) {
        final Member member = memberService.getMemberById(memberId);
        checkDuplicateClubName(clubRegisterRequest.getName());
        final Category category = categoryService.getCategoryById(clubRegisterRequest.getCategoryId());
        final District district = regionService.getDistrictById(clubRegisterRequest.getDistrictId());
        final Club club = Club.createClub(clubRegisterRequest.getName(), clubRegisterRequest.getDescription(), category, clubRegisterRequest.getImageUrl(), district, member.getLoginId());
        clubRepository.save(club);
    }

    private void checkDuplicateClubName(String inputClubName) {
        if (clubRepository.existsByName(inputClubName)) {
            throw new BusinessException(ErrorCode.DUPLICATED_CLUB_NAME);
        }
    }
}
