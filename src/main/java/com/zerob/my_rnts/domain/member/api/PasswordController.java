package com.zerob.my_rnts.domain.member.api;

import com.zerob.my_rnts.domain.member.application.PasswordService;
import com.zerob.my_rnts.domain.member.dto.TokenWithPasswordRequest;
import com.zerob.my_rnts.global.common.ApiResponse;
import com.zerob.my_rnts.global.common.MessageContants;
import com.zerob.my_rnts.global.mail.application.MailService;
import com.zerob.my_rnts.global.mail.dto.VerifiedRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "비밀번호 관리", description = "Password API Document")
public class PasswordController {

    private final PasswordService passwordService;
    private final MailService mailService;

    @Operation(summary = "인증코드 검증 및 비밀번호 검증을 위한 토큰 생성")
    @PostMapping("/public/password/code")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@Valid @RequestBody VerifiedRequest verifiedRequest) {
        boolean isVerified = mailService.verifiedCode(verifiedRequest.getMail().mail(), verifiedRequest.getAuthCode());
        String token = passwordService.requestPasswordReset(isVerified, verifiedRequest.getMail().mail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "비밀번호 변경", description = "토큰을 검증하고 비밀번호를 변경합니다.")
    @PatchMapping("/public/password/change")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody TokenWithPasswordRequest tokenWithPasswordRequest) {
        passwordService.changePassword(tokenWithPasswordRequest.getToken(), tokenWithPasswordRequest.toEntity());
        ApiResponse response = new ApiResponse(MessageContants.SUCCESS_UPDATED);
        return ResponseEntity.ok(response);
    }
}
