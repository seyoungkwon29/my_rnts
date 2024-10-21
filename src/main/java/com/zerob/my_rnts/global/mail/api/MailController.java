package com.zerob.my_rnts.global.mail.api;

import com.zerob.my_rnts.domain.member.application.MemberService;
import com.zerob.my_rnts.global.common.ApiResponse;
import com.zerob.my_rnts.global.mail.application.MailService;
import com.zerob.my_rnts.global.mail.dto.VerificationRequest;
import com.zerob.my_rnts.global.mail.dto.VerifiedRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
@Tag(name = "Mail", description = "Mail API Document")
public class MailController {

    private final MailService mailService;
    private final MemberService memberService;

    @Operation(summary = "회원가입 시, 메일을 통해 인증코드 전송",
            description = "사용자가 입력한 메일(mail)을 통해 인증코드를 전송합니다. 메일이 이미 존재할 경우, 400 에러 반환합니다.")
    @PostMapping("/mail/sign-up")
    public ResponseEntity<ApiResponse> mailForSignUp(@Valid @RequestBody VerificationRequest verificationRequest) throws MessagingException {
        memberService.existedMail(verificationRequest.getMail());
        mailService.sendMail(verificationRequest.getMail().mail());

        ApiResponse response = new ApiResponse("인증코드가 발송되었습니다.");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "아이디 / 비밀번호 찾기 시, 메일을 통해 인증코드 전송",
            description = "사용자가 입력한 메일(mail)을 통해 인증코드를 전송합니다. 메일이 존재하지 않을 경우, 400 에러 반환합니다.")
    @PostMapping("/mail/recovery")
    public ResponseEntity<ApiResponse> mailForRecovery(@Valid @RequestBody VerificationRequest verificationRequest) throws MessagingException {
        memberService.mailNotFound(verificationRequest.getMail());
        mailService.sendMail(verificationRequest.getMail().mail());

        ApiResponse response = new ApiResponse("인증코드가 발송되었습니다.");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "인증코드 검증", description = "사용자가 입력했던 메일(mail)과 인증코드(authCode)를 통해 인증코드를 검증합니다.")
    @PostMapping("/mail/verification")
    public ResponseEntity<ApiResponse> verification(@Valid @RequestBody VerifiedRequest verifiedRequest) {
        boolean isVerified = mailService.verifiedCode(verifiedRequest.getMail().mail(), verifiedRequest.getAuthCode());
        ApiResponse response = new ApiResponse();

        if (isVerified) {
            response.setResult("인증이 완료되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.setResult("인증 실패했습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
