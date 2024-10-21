package com.zerob.my_rnts.global.mail.dto;

import com.zerob.my_rnts.domain.member.vo.Mail;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VerificationRequest {

    @Valid
    private Mail mail;

}
