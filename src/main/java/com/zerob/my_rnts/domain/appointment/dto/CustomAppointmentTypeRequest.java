package com.zerob.my_rnts.domain.appointment.dto;

import com.zerob.my_rnts.domain.appointment.domain.CustomAppointmentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomAppointmentTypeRequest {

    private String typeName;
    private String imageUrl;

    public CustomAppointmentType toEntity() {
        return CustomAppointmentType.builder()
                .typeName(typeName)
                .imageUrl(imageUrl)
                .build();
    }
}
