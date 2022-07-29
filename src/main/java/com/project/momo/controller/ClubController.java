package com.project.momo.controller;

import com.project.momo.common.constatnt.ClubRole;
import com.project.momo.common.utils.AuthUtils;
import com.project.momo.dto.club.ClubJoinRequest;
import com.project.momo.dto.club.ClubRegisterRequest;
import com.project.momo.dto.club.ClubSimpleInfoResponse;
import com.project.momo.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    @GetMapping
    public ResponseEntity<ClubSimpleInfoResponse> inquireClubSimpleInfo(final long clubId) {
        return ResponseEntity.ok(clubService.inquireClubSimpleInfo(clubId));
    }

    @PostMapping("/member")
    public ResponseEntity<?> joinClubAsGeneralMember(@RequestBody final ClubJoinRequest clubJoinRequest) {
        clubService.joinClubAsClubRole(getCurrentMemberId(), clubJoinRequest.getClubId(), ClubRole.MEMBER);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ClubSimpleInfoResponse> registerNewClub(
            @RequestBody @Valid final ClubRegisterRequest clubRegisterRequest
    ) {
        final long memberId = getCurrentMemberId();
        clubService.checkMaxClubCreationPerMember(memberId);
        long clubId = clubService.registerNewClub(clubRegisterRequest, getCurrentMemberId());
        return ResponseEntity.ok(clubService.inquireClubSimpleInfo(clubId));
    }

    private long getCurrentMemberId() {
        return AuthUtils.getMemberId();
    }
}
