package com.zerob.my_rnts.domain.appointment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckInResponse {

    private String message;

    public CheckInResponse(String message) {
        this.message = message;
    }
}
