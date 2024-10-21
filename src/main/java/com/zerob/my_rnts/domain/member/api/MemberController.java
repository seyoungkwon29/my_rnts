package com.zerob.my_rnts.domain.member.api;

import com.zerob.my_rnts.domain.member.application.MemberService;
import com.zerob.my_rnts.domain.member.dto.*;
import com.zerob.my_rnts.global.common.ApiResponse;
import com.zerob.my_rnts.global.common.MessageContants;
import com.zerob.my_rnts.global.mail.application.MailService;
import com.zerob.my_rnts.global.mail.dto.VerifiedRequest;
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
@Tag(name = "회원 관리", description = "Member API Document")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    @Operation(summary = "회원가입", description = "사용자가 입력한 정보를 토대로 회원가입을 진행합니다.")
    @PostMapping("/public/member")
    public ResponseEntity<SignUpResponse> signUp (@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signUp(signUpRequest.toEntity()));
    }

    @Operation(summary = "아이디 중복 확인", description = "회원가입 시 아이디 중복 여부를 확인합니다.")
    @PostMapping("/public/member/check-id")
    public ResponseEntity<ApiResponse> checkLoginId(@Valid @RequestBody CheckLoginIdRequest checkLoginIdRequest) {
        memberService.existedLoginId(checkLoginIdRequest.getLoginId());
        ApiResponse response = new ApiResponse("사용 가능한 아이디 입니다.");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
    @GetMapping("/member")
    public ResponseEntity<MemberDetailResponse> getDetailByLoginId() {
        return ResponseEntity.ok().body(memberService.getDetailByLoginId(this.getPrincipal()));
    }

    @Operation(summary = "사용자 정보 수정")
    @PatchMapping("/member")
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody UpdateRequest updateRequest) {
        memberService.update(updateRequest.toEntity(), this.getPrincipal(), false);
        ApiResponse response = new ApiResponse(MessageContants.SUCCESS_UPDATED);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "사용자 삭제")
    @DeleteMapping("/member")
    public ResponseEntity<ApiResponse> delete() {
        memberService.delete(this.getPrincipal());
        ApiResponse response = new ApiResponse(MessageContants.SUCCESS_DELETED);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "사용자 성향 수정")
    @PatchMapping("/member/tendency")
    public ResponseEntity<ApiResponse> updateTendency(@Valid @RequestBody TendencyRequest tendencyRequest) {
        memberService.update(tendencyRequest.toEntity(), this.getPrincipal(), true);
        ApiResponse response = new ApiResponse(MessageContants.SUCCESS_UPDATED);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "아이디 찾기")
    @PostMapping("/public/member/search-id")
    public ResponseEntity<SearchIdResponse> searchId(@RequestBody VerifiedRequest verifiedRequest) {
        boolean isVerified = mailService.verifiedCode(verifiedRequest.getMail().mail(), verifiedRequest.getAuthCode());
        return ResponseEntity.ok(memberService.searchId(isVerified, verifiedRequest.getMail().mail()));
    }

    public CustomIntegratedUser getPrincipal() {
        return (CustomIntegratedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
