package com.zerob.my_rnts.domain.member.dto;

import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.vo.LoginId;
import com.zerob.my_rnts.domain.member.vo.Password;
import com.zerob.my_rnts.domain.member.vo.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private LoginId loginId;
    private Password password;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .role(RoleType.USER)
                .build();
    }
}