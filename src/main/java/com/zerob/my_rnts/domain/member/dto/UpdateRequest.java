package com.zerob.my_rnts.domain.member.dto;

import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.vo.Nickname;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRequest {

    @Valid
    private Nickname nickname;
    private String profileImage;

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
