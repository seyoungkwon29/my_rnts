package com.zerob.my_rnts.domain.member.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Nickname implements Serializable {

    @NotBlank(message = "닉네임을 입력하세요")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣_-]+$",
            message = "영어 대소문자, 한글 및 _(언더스코어), -(하이픈)만 사용 가능합니다.")
    private String nickname;

    public static Nickname from(final String nickname) {
        return new Nickname(nickname);
    }

    @JsonValue
    public String nickname() {
        return nickname;
    }
}
