package com.zerob.my_rnts.domain.member.dto;


import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.vo.LoginId;
import com.zerob.my_rnts.domain.member.vo.Mail;
import com.zerob.my_rnts.domain.member.vo.Nickname;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetailResponse {

    private Long id;
    private LoginId loginId;
    private Mail mail;
    private Nickname nickname;
    private String profileImage;

    public static MemberDetailResponse of(final Member member) {
        return new MemberDetailResponse(
                member.getId(),
                member.getLoginId(),
                member.getMail(),
                member.getNickname(),
                member.getProfileImage());
    }
}
