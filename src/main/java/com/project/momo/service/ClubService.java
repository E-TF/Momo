package com.project.momo.service;

import com.project.momo.common.constatnt.ClubRole;
import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.common.lock.DistributedLock;
import com.project.momo.common.lock.DistributedLockPrefix;
import com.project.momo.common.lock.LockName;
import com.project.momo.dto.club.ClubRegisterDto;
import com.project.momo.dto.club.ClubSimpleInfoResponse;
import com.project.momo.entity.Club;
import com.project.momo.entity.Consist;
import com.project.momo.entity.Member;
import com.project.momo.repository.ClubRepository;
import com.project.momo.repository.ConsistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubService {

    @Value("${service.club.max-club-size}")
    private int MAX_CLUB_SIZE;
    @Value("${service.club.max-club-creation-per-member}")
    private int MAX_CLUB_CREATION_PER_MEMBER;
    private final ClubRepository clubRepository;
    private final ConsistRepository consistRepository;

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
        Club club = getClubById(clubId);
        return new ClubSimpleInfoResponse(club);
    }

    @DistributedLock(prefix = DistributedLockPrefix.CLUB_NAME)
    @Transactional
    public void registerNewClub(@LockName long memberId, ClubRegisterDto clubRegisterDto) {
        checkMaxClubCreationPerMember(memberId);
        checkDuplicateClubName(clubRegisterDto.getName());
        Club club = clubRegisterDto.toClub();
        clubRepository.save(club);
    }

    @Transactional
    public void joinClubAsClubRole(Member member, Club club, ClubRole clubRole) {
        checkClubSizeOverMaxLimit(club.getId());
        checkDuplicateJoin(member.getId(), club.getId());
        Consist consist = Consist.createConsist(member, club, clubRole);
        consistRepository.save(consist);
    }

    private void checkMaxClubCreationPerMember(long memberId) {
        if (MAX_CLUB_CREATION_PER_MEMBER <=consistRepository.countAllByMemberIdAndRole(memberId, ClubRole.MANAGER)) {
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
