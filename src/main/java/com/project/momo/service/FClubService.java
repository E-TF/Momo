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
    private final ClubService clubService;
    private final ObjectProvider<FClubService> proxy;

    public ClubSimpleInfoResponse inquireClubSimpleInfo(long clubId) {
        return new ClubSimpleInfoResponse(clubService.getClubById(clubId));
    }

    @DistributedLock(prefix = DistributedLockPrefix.CLUB_ID)
    public void joinClubAsClubRoleWithDistributedLock(@LockName final long clubId, final long memberId, ClubRole role) {
        clubService.checkClubSizeOverMaxLimit(clubId);
        clubService.checkDuplicateJoin(memberId, clubId);
        clubService.joinClubAsClubRole(memberId, clubId, role);
    }

    @DistributedLock(prefix = DistributedLockPrefix.MEMBER_ID)
    public void registerNewClubWithMemberIdDistributedLock(@LockName final long memberId,
                                                           ClubRegisterRequest clubRegisterRequest) {
        clubService.checkMaxClubCreationPerMember(memberId);
        proxy
                .getObject()
                .registerNewClubWithClubNameDistributedLock(clubRegisterRequest.getName(), memberId, clubRegisterRequest);
    }

    @DistributedLock(prefix = DistributedLockPrefix.CLUB_NAME)
    public void registerNewClubWithClubNameDistributedLock(@LockName final String clubName, final long memberId,
                                                           ClubRegisterRequest clubRegisterRequest) {
        clubService.checkDuplicateClubName(clubName);
        clubService.registerNewClub(memberId, clubRegisterRequest);
    }

    public long getClubByName(String name) {
        return clubService.getClubByName(name).getId();
    }
}
