package com.zerob.my_rnts.domain.appointment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerob.my_rnts.domain.appointment.domain.Appointment;
import com.zerob.my_rnts.domain.appointment.vo.AppointmentType;
import com.zerob.my_rnts.domain.appointment.vo.Location;
import com.zerob.my_rnts.domain.appointment.vo.Title;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentUpdateRequest {

    @Valid @JsonProperty("title")
    private Title title;

    @Valid @JsonProperty("appointmentType")
    private AppointmentType appointmentType;

    @Valid @JsonProperty("appointmentTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentTime;

    @Valid @JsonProperty("location")
    private Location location;

    // 정적 팩토리 메서드 추가
    public static AppointmentUpdateRequest of(final Appointment appointment) {
        return new AppointmentUpdateRequest(appointment.getTitle(), appointment.getAppointmentType(),
                appointment.getAppointmentTime(), appointment.getLocation());
    }

    @Builder
    public Appointment toEntity() {
        return Appointment.builder()
                .title(title)
                .appointmentType(appointmentType)
                .appointmentTime(appointmentTime)
                .location(Location.from(this.location.getPlace(), this.location.getLatitude(), this.location.getLongitude()))
                .build();
    }
}
