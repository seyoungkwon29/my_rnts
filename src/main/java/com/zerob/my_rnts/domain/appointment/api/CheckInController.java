package com.zerob.my_rnts.domain.appointment.api;

import com.zerob.my_rnts.domain.appointment.application.CheckInServcie;
import com.zerob.my_rnts.domain.appointment.dto.CheckInResponse;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CheckInController {

    private final CheckInServcie checkInServcie;

    @PostMapping("/check-in/{appointmentId}")
    public ResponseEntity<CheckInResponse> checkIn(@PathVariable Long appointmentId) {
        CheckInResponse response = checkInServcie.checkIn(getPrincipal().getMember().getId(), appointmentId);

        return ResponseEntity.ok(response);
    }

    public CustomIntegratedUser getPrincipal() {
        return (CustomIntegratedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
