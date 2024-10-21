package com.zerob.my_rnts.domain.member.dto;

import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.vo.LoginId;
import com.zerob.my_rnts.domain.member.vo.Mail;
import com.zerob.my_rnts.domain.member.vo.Nickname;
import com.zerob.my_rnts.domain.member.vo.Tendency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpResponse {

    private Long id;
    private LoginId loginId;
    private Mail mail;
    private Nickname nickname;
    private String profileImage;
    private Tendency tendency;

    public static SignUpResponse of(final Member member) {
        return new SignUpResponse(
                member.getId(),
                member.getLoginId(),
                member.getMail(),
                member.getNickname(),
                member.getProfileImage(),
                member.getTendency());
    }
}
