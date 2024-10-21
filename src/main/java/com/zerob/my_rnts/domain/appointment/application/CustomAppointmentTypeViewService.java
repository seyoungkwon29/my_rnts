package com.zerob.my_rnts.domain.appointment.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentIconResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomAppointmentTypeViewService {

    private final AmazonS3Client objectStorageClient;

    public AppointmentIconResponse getIconList(final String bucketName) {
        List<String> fileNames = new ArrayList<>();
        ListObjectsRequest request = new ListObjectsRequest();
        request.setBucketName(bucketName);
        request.setPrefix("icon/");

        ObjectListing objectListing = objectStorageClient.listObjects(request);
        for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
            fileNames.add(summary.getKey());
        }

        fileNames.remove(0); // 폴더는 리스트에서 제거

        return AppointmentIconResponse.from(fileNames);
    }
}
