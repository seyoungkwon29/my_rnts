package com.zerob.my_rnts.global.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.zerob.my_rnts.global.file.exception.FileErrorCode;
import com.zerob.my_rnts.global.file.exception.FileException;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    @Value("${ncp.storage.bucketname}")
    private String BUCKET_NAME;
    private final AmazonS3Client objectStorageClient;

    public String uploadFileToBucket(MultipartFile file, String fileName, ObjectMetadata objectMetadata, CustomIntegratedUser user) {
        // 사용자 Login ID로 폴더 생성 및 중복 방지를 위한 고유값 + 파일명 생성
        String keyName = createFileName(user.getUsername(), fileName);

        try {
            PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, keyName, file.getInputStream(), objectMetadata);
            objectStorageClient.putObject(request);
        } catch (IOException e) {
            throw new FileException(FileErrorCode.FAILED_TO_UPLOAD_FILE);
        }
        return objectStorageClient.getUrl(BUCKET_NAME, keyName).toString();
    }

    public String deleteFileFromBucket(String url) {
        String splitStr = ".com/";
        String keyName = url.substring(url.lastIndexOf(splitStr) + splitStr.length());

        log.info("keyName : {}", keyName);

        if (objectStorageClient.doesObjectExist(BUCKET_NAME, keyName)) {
            DeleteObjectRequest request = new DeleteObjectRequest(BUCKET_NAME, keyName);
            objectStorageClient.deleteObject(request);
            return "성공적으로 삭제되었습니다.";
        } else
            return "파일이 존재하지 않습니다.";
    }

    private String createFileName(final String loginId, final String fileName) {
        return loginId + "/" + UUID.randomUUID().toString().concat(fileName);
    }
}
