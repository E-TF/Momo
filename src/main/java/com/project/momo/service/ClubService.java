package com.project.momo.service;

import com.project.momo.common.constatnt.ClubRole;
import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.club.ClubRegisterRequest;
import com.project.momo.entity.*;
import com.project.momo.repository.ClubRepository;
import com.project.momo.repository.ConsistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final ConsistRepository consistRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final RegionService regionService;
    @Value("${service.club.max-club-size}")
    private int MAX_CLUB_SIZE;
    @Value("${service.club.max-club-creation-per-member}")
    private int MAX_CLUB_CREATION_PER_MEMBER;

    public Club getClubById(long clubId) {
        return clubRepository
                .findById(clubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));
    }

    public Consist getConsistById(long memberId, long clubId) {
        return consistRepository
                .findByMemberIdAndClubId(memberId, clubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CONSIST_NOT_FOUND));
    }

    public Club getClubByName(String clubName) {
        return clubRepository
                .findByName(clubName)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.CLUB_NOT_FOUND)
                );
    }

    @Transactional
    public long registerNewClub(final long memberId, ClubRegisterRequest clubRegisterRequest) {
        final Member member = memberService.getMemberById(memberId);
        final Category category = categoryService.getCategoryById(clubRegisterRequest.getCategoryId());
        categoryService.checkCategoryLevelChild(category);
        final District district = regionService.getDistrictById(clubRegisterRequest.getDistrictId());
        final Club club = Club.createClub(clubRegisterRequest.getName(),
                clubRegisterRequest.getDescription(),
                category, clubRegisterRequest.getImageUrl(),
                district, member.getLoginId());
        clubRepository.save(club);
        return club.getId();
    }

    @Transactional
    public void joinClubAsClubRole(long memberId, long clubId, ClubRole clubRole) {
        Member member = memberService.getMemberById(memberId);
        Club club = getClubById(clubId);
        consistRepository.save(Consist.createConsist(member, club, clubRole));
    }

    public void checkMaxClubCreationPerMember(long memberId) {
        if (MAX_CLUB_CREATION_PER_MEMBER <= consistRepository.countAllByMemberIdAndRole(memberId, ClubRole.MANAGER)) {
            throw new BusinessException(ErrorCode.EXCEED_CLUB_CREATION_LIMIT_PER_MEMBER);
        }
    }

    public void checkDuplicateJoin(Long memberId, Long clubId) {
        if (consistRepository.existsByMemberIdAndClubId(memberId, clubId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_CLUB_JOIN);
        }
    }

    public void checkClubSizeOverMaxLimit(long clubId) {
        if (consistRepository.countAllByClubId(clubId) >= MAX_CLUB_SIZE) {
            throw new BusinessException(ErrorCode.EXCEED_CLUB_SIZE_LIMIT);
        }
    }

    public void checkDuplicateClubName(String inputClubName) {
        if (clubRepository.existsByName(inputClubName)) {
            throw new BusinessException(ErrorCode.DUPLICATED_CLUB_NAME);
        }
    }
}
