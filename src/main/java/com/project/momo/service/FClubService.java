package com.project.momo.service;

import com.project.momo.common.constatnt.ClubRole;
import com.project.momo.common.lock.DistributedLock;
import com.project.momo.common.lock.DistributedLockPrefix;
import com.project.momo.common.lock.LockName;
import com.project.momo.dto.club.ClubRegisterRequest;
import com.project.momo.dto.club.ClubSimpleInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FClubService {
    private final MemberService memberService;
    private final ClubService clubService;
    private final ObjectProvider<FClubService> proxy;

    public ClubSimpleInfoResponse inquireClubSimpleInfo(long clubId) {
        return new ClubSimpleInfoResponse(clubService.getClubById(clubId));
    }

    @DistributedLock(prefix = DistributedLockPrefix.CLUB_ID)
    public void joinClubAsClubRole(@LockName final long clubId, final long memberId, ClubRole role) {
        memberService.getMemberById(memberId);
        clubService.checkClubSizeOverMaxLimit(clubId);
        clubService.checkDuplicateJoin(memberId, clubId);
        clubService.joinClubAsClubRole(memberId, clubId, role);
    }

    @DistributedLock(prefix = DistributedLockPrefix.MEMBER_ID)
    public long checkClubCreationLimitAndRegisterNewClub(@LockName final long memberId,
                                                         final ClubRegisterRequest clubRegisterRequest) {
        memberService.getMemberById(memberId);
        clubService.checkMaxClubCreationPerMember(memberId);
        final long clubId = proxy
                .getObject()
                .registerNewClub(clubRegisterRequest.getName(), memberId, clubRegisterRequest);
        proxy.getObject().joinClubAsClubRole(clubId, memberId, ClubRole.MANAGER);
        return clubId;
    }

    @DistributedLock(prefix = DistributedLockPrefix.CLUB_NAME)
    public long registerNewClub(@LockName final String clubName, final long memberId,
                                ClubRegisterRequest clubRegisterRequest) {
        clubService.checkDuplicateClubName(clubName);
        return clubService.registerNewClub(memberId, clubRegisterRequest);
    }
}
