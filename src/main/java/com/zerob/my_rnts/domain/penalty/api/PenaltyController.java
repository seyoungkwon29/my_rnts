package com.zerob.my_rnts.domain.penalty.api;

import com.zerob.my_rnts.domain.penalty.application.PenaltyService;
import com.zerob.my_rnts.domain.penalty.dto.PenaltyRequest;
import com.zerob.my_rnts.domain.penalty.dto.PenaltyResponse;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "패널티 관리", description = "Penalty API Document")
public class PenaltyController {

    private final PenaltyService penaltyService;

    @Operation(summary = "패널티 생성", description = "1등 : 패널티 생성")
    @PostMapping("/penalty")
    public ResponseEntity<PenaltyResponse> create(@Valid PenaltyRequest penaltyRequest) {

    }

    private CustomIntegratedUser getPrincipal() {
        return (CustomIntegratedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
