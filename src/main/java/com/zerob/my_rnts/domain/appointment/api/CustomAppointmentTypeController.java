package com.zerob.my_rnts.domain.appointment.api;


import com.zerob.my_rnts.domain.appointment.application.CustomAppointmentTypeService;
import com.zerob.my_rnts.domain.appointment.dto.CustomAppointmentTypeRequest;
import com.zerob.my_rnts.domain.appointment.dto.CustomAppointmentTypeResponse;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "사용자 정의 약속 유형", description = "Custom Appointment Type API Document")
public class CustomAppointmentTypeController {

    private final CustomAppointmentTypeService customAppointmentTypeService;

    @Operation(summary = "사용자 정의 약속 유형 생성")
    @PostMapping("/appointment-type")
    public ResponseEntity<CustomAppointmentTypeResponse> create(@Valid @RequestBody CustomAppointmentTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customAppointmentTypeService.create(request.toEntity(), this.getPrincipal()));
    }

    @Operation(summary = "사용자 정의 약속 유형 조회", description = "사용자가 정의한 약속 유형을 List로 반환합니다.")
    @GetMapping("/appointment-types")
    public ResponseEntity<List<CustomAppointmentTypeResponse>> getCustomAppointmentType() {
        return ResponseEntity.ok().body(customAppointmentTypeService.getCustomAppointmentTypeList(this.getPrincipal()));
    }

    @Operation(summary = "사용자 정의 약속 유형 수정")
    @PatchMapping("/appointment-type/{customAppointmentTypeId}")
    public ResponseEntity<CustomAppointmentTypeResponse> update(@Valid @RequestBody CustomAppointmentTypeRequest request,
                                                                @PathVariable Long customAppointmentTypeId) {
        return ResponseEntity.ok().body(customAppointmentTypeService.update(request.toEntity(), customAppointmentTypeId));
    }

    @Operation(summary = "사용자 정의 약속 유형 삭제")
    @DeleteMapping("/appointment-type/{customAppointmentTypeId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long customAppointmentTypeId) {
        customAppointmentTypeService.delete(customAppointmentTypeId);
        ApiResponse response = new ApiResponse(MessageContants.SUCCESS_DELETED);
        return ResponseEntity.ok(response);
    }

    public CustomIntegratedUser getPrincipal() {
        return (CustomIntegratedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
