package com.project.momo.controller;

import com.project.momo.common.utils.AuthUtils;
import com.project.momo.dto.club.ClubRegisterRequest;
import com.project.momo.dto.club.ClubSimpleInfoResponse;
import com.project.momo.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    @GetMapping
    public ResponseEntity<ClubSimpleInfoResponse> inquireClubSimpleInfo(long clubId) {
        return ResponseEntity.ok(clubService.inquireClubSimpleInfo(clubId));
    }

    @PostMapping
    public ResponseEntity<ClubSimpleInfoResponse> registerNewClub(@RequestBody ClubRegisterRequest clubRegisterRequest) {
        clubService.registerNewClub(clubRegisterRequest, getCurrentMemberId());
        long clubId = clubService.getClubByName(clubRegisterRequest.getName()).getId();
        return ResponseEntity.ok(clubService.inquireClubSimpleInfo(clubId));
    }

    private long getCurrentMemberId() {
        return AuthUtils.getMemberId();
    }
}
