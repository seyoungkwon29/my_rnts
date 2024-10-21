package com.zerob.my_rnts.domain.appointment.api;


import com.zerob.my_rnts.domain.appointment.application.CustomAppointmentTypeViewService;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentIconResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "사용자 정의 약속 유형", description = "Custom Appointment Type API Document")
public class CustomAppointmentTypeViewController {

    private final CustomAppointmentTypeViewService customAppointmentTypeViewService;

    @Operation(summary = "약속 유형 아이콘 조회", description = "약속 유형 아이콘을 List로 반환합니다.")
    @GetMapping("/icons/{bucketName}")
    public ResponseEntity<AppointmentIconResponse> getIconList(@PathVariable("bucketName") String bucketName) {
        return ResponseEntity.ok().body(customAppointmentTypeViewService.getIconList(bucketName));
    }
}
