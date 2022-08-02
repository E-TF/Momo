package com.project.momo.controller;

import com.project.momo.common.constatnt.ClubRole;
import com.project.momo.common.utils.AuthUtils;
import com.project.momo.dto.club.ClubJoinRequest;
import com.project.momo.dto.club.ClubRegisterRequest;
import com.project.momo.dto.club.ClubSimpleInfoResponse;
import com.project.momo.service.FClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clubs")
public class ClubController {
    private final FClubService fClubService;

    @GetMapping
    public ResponseEntity<ClubSimpleInfoResponse> inquireClubSimpleInfo(final long clubId) {
        return ResponseEntity.ok(fClubService.inquireClubSimpleInfo(clubId));
    }

    @PostMapping("/member")
    public ResponseEntity<?> joinClubAsMember(@RequestBody final ClubJoinRequest clubJoinRequest) {
        fClubService
                .joinClubAsClubRoleWithDistributedLock(clubJoinRequest.getClubId(), getCurrentMemberId(), ClubRole.MEMBER);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ClubSimpleInfoResponse> registerNewClub(
            @RequestBody @Valid final ClubRegisterRequest clubRegisterRequest
    ) {
        final long memberId = getCurrentMemberId();
        fClubService.registerNewClubWithMemberIdDistributedLock(memberId, clubRegisterRequest);
        final long clubId = fClubService.getClubByName(clubRegisterRequest.getName());
        fClubService.joinClubAsClubRoleWithDistributedLock(clubId, memberId, ClubRole.MANAGER);
        return ResponseEntity.ok(fClubService.inquireClubSimpleInfo(clubId));
    }

    private long getCurrentMemberId() {
        return AuthUtils.getMemberId();
    }
}
