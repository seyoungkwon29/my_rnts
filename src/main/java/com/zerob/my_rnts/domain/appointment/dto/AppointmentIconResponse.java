package com.zerob.my_rnts.domain.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentIconResponse {

    private List<String> fileNames;

    public static AppointmentIconResponse from(List<String> fileNames) {
        return new AppointmentIconResponse(fileNames);
    }
}
