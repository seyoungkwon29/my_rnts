package com.zerob.my_rnts.domain.member.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginId implements Serializable {

    @NotBlank(message = "아이디를 입력하세요.")
    private String loginId;

    public static LoginId from(final String loginId) {
        return new LoginId(loginId);
    }

    @JsonValue
    public String loginId() {
        return loginId;
    }
}
