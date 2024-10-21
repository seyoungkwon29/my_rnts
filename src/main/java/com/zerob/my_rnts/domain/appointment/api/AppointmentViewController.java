package com.zerob.my_rnts.domain.appointment.api;

import com.zerob.my_rnts.domain.appointment.application.AppointmentViewService;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentResponse;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentViewResponse;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "약속 조회", description = "Appointment API Document")
public class AppointmentViewController {

    private final AppointmentViewService appointmentViewService;

    @Operation(summary = "내 모든 약속 조회")
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getAllAppointments(this.getPrincipal()));
    }

    @Operation(summary = "지난 약속 조회")
    @GetMapping("/appointments/past")
    public ResponseEntity<List<AppointmentResponse>> getPastAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getPastAppointments(this.getPrincipal()));
    }

    @Operation(summary = "남은 약속 조회")
    @GetMapping("/appointments/upcoming")
    public ResponseEntity<List<AppointmentResponse>> getUpcomingAppointments() {
        return ResponseEntity.ok().body(appointmentViewService.getUpcomingAppointments(this.getPrincipal()));
    }

    public CustomIntegratedUser getPrincipal() {
        return (CustomIntegratedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
