package com.project.momo.controller;

import com.project.momo.common.constatnt.ClubRole;
import com.project.momo.common.utils.AuthUtils;
import com.project.momo.dto.club.ClubJoinRequest;
import com.project.momo.dto.club.ClubRegisterDto;
import com.project.momo.dto.club.ClubRegisterRequest;
import com.project.momo.dto.club.ClubSimpleInfoResponse;
import com.project.momo.entity.Category;
import com.project.momo.entity.Club;
import com.project.momo.entity.District;
import com.project.momo.entity.Member;
import com.project.momo.service.CategoryService;
import com.project.momo.service.ClubService;
import com.project.momo.service.MemberService;
import com.project.momo.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<ClubSimpleInfoResponse> inquireClubSimpleInfo(long clubId) {
        return ResponseEntity.ok(clubService.inquireClubSimpleInfo(clubId));
    }

    @PostMapping
    public ResponseEntity<ClubSimpleInfoResponse> registerNewClub(@RequestBody ClubRegisterRequest clubRegisterRequest) {
        Member member = memberService.getMemberById(getCurrentMemberId());
        Category category = categoryService.getCategoryById(clubRegisterRequest.getCategoryId());
        District district = regionService.getDistrictById(clubRegisterRequest.getDistrictId());
        clubService.registerNewClub(member.getId(), new ClubRegisterDto(clubRegisterRequest, category, district, member.getLoginId()));
        Club club = clubService.getClubByName(clubRegisterRequest.getName());
        clubService.joinClubAsClubRole(member, club, ClubRole.MANAGER);
        return ResponseEntity.ok(clubService.inquireClubSimpleInfo(club.getId()));
    }

    @PostMapping("/member")
    public ResponseEntity<?> joinClubAsGeneralMember(@RequestBody ClubJoinRequest clubJoinRequest) {
        Member member = memberService.getMemberById(getCurrentMemberId());
        Club club = clubService.getClubById(clubJoinRequest.getClubId());
        clubService.joinClubAsClubRole(member, club, ClubRole.GENERAL);
        return ResponseEntity.ok().build();
    }

    private long getCurrentMemberId() {
        return AuthUtils.getMemberId();
    }
}
