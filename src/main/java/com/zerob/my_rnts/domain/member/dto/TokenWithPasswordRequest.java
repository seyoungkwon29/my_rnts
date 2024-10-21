package com.zerob.my_rnts.domain.member.dto;

import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.vo.Password;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenWithPasswordRequest {

    private String token;
    @Valid
    private Password password;

    public Member toEntity() {
        return Member.builder()
                .password(password)
                .build();
    }
}
