package com.zerob.my_rnts.global.file;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.zerob.my_rnts.global.common.ApiResponse;
import com.zerob.my_rnts.global.common.MessageContants;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {

    private final FileService fileService;

    @PostMapping("/file/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // Object Metadata 생성
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        // 파일 업로드 후 URL 반환
        String fileUrl = fileService.uploadFileToBucket(file, file.getOriginalFilename(), objectMetadata, getPrincipal());
        return ResponseEntity.ok(fileUrl);
    }

    @DeleteMapping("/file/delete")
    public ResponseEntity<ApiResponse> deleteFile(@RequestBody DeleteRequest request) {
        String result = fileService.deleteFileFromBucket(request.getUrl());
        ApiResponse response = new ApiResponse(result);

        return ResponseEntity.ok(response);
    }

    public CustomIntegratedUser getPrincipal() {
        return (CustomIntegratedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
