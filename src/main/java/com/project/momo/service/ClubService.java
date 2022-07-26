package com.project.momo.service;

import com.project.momo.common.constatnt.ClubRole;
import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.club.ClubRegisterRequest;
import com.project.momo.dto.club.ClubSimpleInfoResponse;
import com.project.momo.entity.*;
import com.project.momo.repository.ClubRepository;
import com.project.momo.repository.ConsistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ConsistRepository consistRepository;
    @Value("${service.club.max-club-size}")
    private int MAX_CLUB_SIZE;
    @Value("${service.club.max-club-creation-per-member}")
    private int MAX_CLUB_CREATION_PER_MEMBER;

    @Transactional(readOnly = true)
    public Club getClubById(long clubId) {
        return clubRepository
                .findById(clubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Consist getConsistById(long memberId, long clubId) {
        return consistRepository
                .findByMemberIdAndClubId(memberId, clubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CONSIST_NOT_FOUND));
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

    @Transactional
    public long registerNewClub(ClubRegisterRequest clubRegisterRequest, final Member member, final Category category, final District district) {
        checkDuplicateClubName(clubRegisterRequest.getName());
        final Club club = Club.createClub(clubRegisterRequest.getName(),
                clubRegisterRequest.getDescription(),
                category, clubRegisterRequest.getImageUrl(),
                district, member.getLoginId());
        clubRepository.save(club);
        joinClubAsClubRole(member, club, ClubRole.MANAGER);
        return club.getId();
    }

    @Transactional
    public void joinClubAsClubRole(Member member, Club club, ClubRole clubRole) {
        checkClubSizeOverMaxLimit(club.getId());
        checkDuplicateJoin(member.getId(), club.getId());
        Consist consist = Consist.createConsist(member, club, clubRole);
        consistRepository.save(consist);
    }

    @Transactional(readOnly = true)
    public void checkMaxClubCreationPerMember(long memberId) {
        if (MAX_CLUB_CREATION_PER_MEMBER <= consistRepository.countAllByMemberIdAndRole(memberId, ClubRole.MANAGER)) {
            throw new BusinessException(ErrorCode.EXCEED_CLUB_CREATION_LIMIT_PER_MEMBER);
        }
    }

    private void checkDuplicateJoin(Long memberId, Long clubId) {
        if (consistRepository.existsByMemberIdAndClubId(memberId, clubId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_CLUB_JOIN);
        }
    }

    private void checkClubSizeOverMaxLimit(long clubId) {
        if (consistRepository.countAllByClubId(clubId) >= MAX_CLUB_SIZE) {
            throw new BusinessException(ErrorCode.EXCEED_CLUB_SIZE_LIMIT);
        }
    }

    private void checkDuplicateClubName(String inputClubName) {
        if (clubRepository.existsByName(inputClubName)) {
            throw new BusinessException(ErrorCode.DUPLICATED_CLUB_NAME);
        }
    }
}
