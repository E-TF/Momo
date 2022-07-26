package com.project.momo.controller;

import com.project.momo.common.exception.ErrorDto;
import com.project.momo.common.exception.s3.DuplicatedFileNameException;
import com.project.momo.common.exception.s3.EmptyFileException;
import com.project.momo.common.exception.s3.S3MultipartConversionException;
import com.project.momo.common.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Validated
public class S3Controller {
    private final S3Uploader s3Uploader;
    private final String MEMBER_PROFILE_DIRECTORY_NAME = "member-profile";
    private final String CLUB_PROFILE_DIRECTORY_NAME = "club-profile";
    private final String CATEGORY_TYPE_PROFILE_DIRECTORY_NAME = "category-profile";

    @PostMapping("/member/profile/upload")
    public String uploadMemberProfile(@RequestParam("image") final MultipartFile multipartFile)
            throws S3MultipartConversionException, DuplicatedFileNameException {
        checkFileSize(multipartFile);
        return s3Uploader.upload(multipartFile, MEMBER_PROFILE_DIRECTORY_NAME);
    }

    @PostMapping("/club/profile/upload")
    public String uploadClubProfile(@RequestParam("image") final MultipartFile multipartFile)
            throws S3MultipartConversionException, DuplicatedFileNameException {
        return s3Uploader.upload(multipartFile, CLUB_PROFILE_DIRECTORY_NAME);
    }

    @PostMapping("/admin/category/profile/upload")
    public String uploadCategoryTypeProfile(@RequestParam("image") final MultipartFile multipartFile)
            throws S3MultipartConversionException, DuplicatedFileNameException {
        return s3Uploader.upload(multipartFile, CATEGORY_TYPE_PROFILE_DIRECTORY_NAME);
    }

    private void checkFileSize(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw EmptyFileException.getInstance();
        }
    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<ErrorDto> handleEmptyFileException(EmptyFileException emptyFileException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(emptyFileException));
    }
}
