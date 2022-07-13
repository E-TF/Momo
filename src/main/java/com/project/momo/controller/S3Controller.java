package com.project.momo.controller;

import com.project.momo.common.exception.s3.DuplicatedFileNameException;
import com.project.momo.common.exception.s3.S3MultipartConversionException;
import com.project.momo.common.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class S3Controller {

    private final S3Uploader s3Uploader;
    private final String MEMBER_PROFILE_DIRECTORY_NAME = "member-profile";
    private final String CLUB_PROFILE_DIRECTORY_NAME = "club-profile";
    private final String CATEGORY_TYPE_PROFILE_DIRECTORY_NAME = "category-profile";

    @PostMapping("/api/member/profile/upload")
    public String uploadMemberProfile(@RequestParam("image") MultipartFile multipartFile) throws S3MultipartConversionException, DuplicatedFileNameException {
        return s3Uploader.upload(multipartFile, MEMBER_PROFILE_DIRECTORY_NAME);
    }

    @PostMapping("/api/club/profile/upload")
    public String uploadClubProfile(@RequestParam("image") MultipartFile multipartFile) throws S3MultipartConversionException, DuplicatedFileNameException {
        return s3Uploader.upload(multipartFile, CLUB_PROFILE_DIRECTORY_NAME);
    }

    @PostMapping("/api/category/profile/upload")
    public String uploadCategoryTypeProfile(@RequestParam("image") MultipartFile multipartFile) throws S3MultipartConversionException, DuplicatedFileNameException{
        return s3Uploader.upload(multipartFile, CATEGORY_TYPE_PROFILE_DIRECTORY_NAME);
    }
}
