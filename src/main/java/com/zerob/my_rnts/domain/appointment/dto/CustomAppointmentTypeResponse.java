package com.zerob.my_rnts.domain.appointment.dto;

import com.zerob.my_rnts.domain.appointment.domain.CustomAppointmentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomAppointmentTypeResponse {

    private Long id;
    private String typeName;
    private String imageUrl;

    public static CustomAppointmentTypeResponse of(final CustomAppointmentType customAppointmentType) {
        return new CustomAppointmentTypeResponse(
                customAppointmentType.getId(),
                customAppointmentType.getTypeName(),
                customAppointmentType.getImageUrl());
    }
}
