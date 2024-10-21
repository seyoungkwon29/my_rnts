package com.zerob.my_rnts.domain.appointment.api;

import com.zerob.my_rnts.domain.appointment.application.AppointmentService;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentDetailsResponse;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentRequest;
import com.zerob.my_rnts.domain.appointment.dto.AppointmentResponse;
import com.zerob.my_rnts.global.common.ApiResponse;
import com.zerob.my_rnts.global.common.MessageContants;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "약속 관리", description = "Appointment API Document")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "약속 생성", description = "제목과 약속 정보를 이용하여 새로운 약속을 생성합니다.")
    @PostMapping("/appointment")
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentRequest appointmentRequest) {

        if (appointmentRequest.getCustomAppointmentTypeId() == null) appointmentRequest.setCustomTypeIdZero();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.create(appointmentRequest.toEntity(), this.getPrincipal()));
    }

    @Operation(summary = "초대 받은 약속 정보 확인")
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<AppointmentDetailsResponse> getAppointmentDetails(@PathVariable Long appointmentId) {
        return ResponseEntity.ok().body(appointmentService.getAppointmentDetails(appointmentId));
    }

    @Operation(summary = "약속 정보 수정", description = "해당 약속에 대한 정보를 수정할 수 있습니다.")
    @PatchMapping("/appointment/{appointmentId}")
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody AppointmentRequest appointmentRequest,
                                              @PathVariable Long appointmentId) {
        appointmentService.udpate(appointmentRequest.toEntity(), appointmentId);
        ApiResponse response = new ApiResponse(MessageContants.SUCCESS_UPDATED);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "약속 빠지기",
            description = "해당 약속에서 사용자가 제외됩니다. 모든 사용자가 약속에서 빠질 경우, 자동으로 약속이 삭제됩니다.")
    @DeleteMapping("/appointment/{appointmentId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long appointmentId) {
        appointmentService.delete(appointmentId, this.getPrincipal());
        ApiResponse response = new ApiResponse(MessageContants.SUCCESS_DELETED);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "약속 초대 처리", description = "파라미터가 'accept'일 경우, 해당 약속에 사용자가 추가됩니다. 파라미터가 'decline'일 경우, 해당 약속에 추가되지 않습니다.")
    @GetMapping("/appointment/invite/{appointmentId}")
    public ResponseEntity<ApiResponse> invite(@PathVariable Long appointmentId, @RequestParam("status") String status) {
        // 초대 수락
        if ("accept".equalsIgnoreCase(status)) {
            appointmentService.acceptInvite(appointmentId, this.getPrincipal());
            ApiResponse response = new ApiResponse("초대를 수락했습니다.");
            return ResponseEntity.ok(response);
        }
        // 초대 거절
        else if ("decline".equalsIgnoreCase(status)) {
            ApiResponse response = new ApiResponse("초대를 거절했습니다.");
            return ResponseEntity.ok(response);
        }
        // 잘못된 status 값이 전달된 경우
        else {
            ApiResponse response = new ApiResponse("잘못된 요청입니다. status 값은 'accept' 또는 'decline'이어야 합니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    public CustomIntegratedUser getPrincipal() {
        return (CustomIntegratedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
