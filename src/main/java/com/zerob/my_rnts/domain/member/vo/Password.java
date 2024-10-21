package com.zerob.my_rnts.domain.member.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password implements Serializable {

    @NotBlank(message = "비밀번호를 입력하세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "영어 대소문자, 숫자, 특수문자를 포함한 8 ~ 16자리 여야 합니다.")
    private String password;

    public static Password from(final String password) {
        return new Password(password);
    }

    public static Password encode(final String password, final PasswordEncoder passwordEncoder) {
        return new Password(passwordEncoder.encode(password));
    }

    @JsonValue
    public String password() {
        return password;
    }
}
