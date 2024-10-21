package com.zerob.my_rnts.domain.member.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mail implements Serializable {

    @jakarta.validation.constraints.Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일을 입력하세요.")
    private String mail;

    private boolean status;

    @Builder
    public Mail(String mail) {
        this.mail = mail;
        this.status = false;
    }

    public static Mail from(final String mail) {
        return new Mail(mail);
    }

    @JsonValue
    public String mail() {
        return mail;
    }
}
