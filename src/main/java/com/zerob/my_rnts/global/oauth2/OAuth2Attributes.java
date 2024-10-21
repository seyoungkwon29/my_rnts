package com.zerob.my_rnts.global.oauth2;

import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.vo.*;
import com.zerob.my_rnts.global.oauth2.userInfo.OAuth2UserInfo;
import com.zerob.my_rnts.global.oauth2.userInfo.KakaoOAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class OAuth2Attributes {

    private final String nameAttributeKey;
    private final OAuth2UserInfo oAuth2UserInfo;

    public static OAuth2Attributes of(SocialType socialType, String userNameAttributeName, Map<String, Object> attriubtes) {
        if (Objects.equals(socialType, SocialType.KAKAO))
            return ofKakao(userNameAttributeName, attriubtes);
        return null;
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attriubtes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attriubtes))
                .build();
    }

    public Member toEntity(final String oAuthLoginId, final OAuth2UserInfo oAuth2UserInfo) {
        return Member.builder()
                .loginId(LoginId.from(oAuthLoginId))
                .mail(Mail.from(oAuth2UserInfo.getMail()))
                .password(Password.from(UUID.randomUUID().toString())) // 임시 비밀번호 (소셜 로그인은 비밀번호가 없음)
                .nickname(Nickname.from(oAuth2UserInfo.getNickname()))
                .profileImage(oAuth2UserInfo.getProfileImage())
                .role(RoleType.USER)
                .socialType(SocialType.KAKAO)
                .socialId(oAuth2UserInfo.getSocialId())
                .build();
    }
}
