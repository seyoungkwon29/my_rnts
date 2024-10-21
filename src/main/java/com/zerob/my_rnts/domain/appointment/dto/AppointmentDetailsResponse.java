package com.zerob.my_rnts.domain.appointment.dto;

import com.zerob.my_rnts.domain.appointment.domain.Appointment;
import com.zerob.my_rnts.domain.appointment.vo.Title;
import com.zerob.my_rnts.domain.member.vo.Nickname;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentDetailsResponse {

    @Valid
    private Nickname from;

    @Valid
    private Title title;

    @Valid
    private String place;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentTime;

    public static AppointmentDetailsResponse of(final Nickname from, final Appointment appointment) {
        return new AppointmentDetailsResponse(
                from,
                appointment.getTitle(),
                appointment.getLocation().getPlace(),
                appointment.getAppointmentTime()
        );
    }

}
