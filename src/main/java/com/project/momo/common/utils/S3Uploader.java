package com.project.momo.common.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.project.momo.common.exception.s3.DuplicatedFileNameException;
import com.project.momo.common.exception.s3.S3MultipartConversionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws S3MultipartConversionException, DuplicatedFileNameException {
        try {
            File convertFile = convert(multipartFile);
            return upload(convertFile, dirName);
        } catch (IOException exception) {
            throw S3MultipartConversionException.getInstance();
        }
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private void removeNewFile(File targetFile) {
        if (!targetFile.delete())
            log.error("파일이 정상적으로 삭제되지 않았습니다.\n" +
                    "File Path : " + targetFile.getAbsolutePath() + "\n" +
                    "File Name : " + targetFile.getName());
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private File convert(MultipartFile multipartFile) throws IOException, DuplicatedFileNameException {
        File convertFile = new File(multipartFile.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return convertFile;
        } else
            throw new DuplicatedFileNameException(multipartFile.getOriginalFilename());
    }
}
