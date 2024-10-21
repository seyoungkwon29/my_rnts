package com.zerob.my_rnts.global.oauth2.application;

import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.repository.MemberRepository;
import com.zerob.my_rnts.domain.member.vo.LoginId;
import com.zerob.my_rnts.domain.member.vo.SocialType;
import com.zerob.my_rnts.global.oauth2.OAuth2Attributes;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        SocialType socialType = getSocialType(registrationId);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attriubtes = oAuth2User.getAttributes();

        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(socialType, userNameAttributeName, attriubtes);

        assert oAuth2Attributes != null;
        String oAuth2LoginId = registrationId + "_" + oAuth2Attributes.getOAuth2UserInfo().getSocialId();

        Member member = loadOrSaveUser(oAuth2LoginId, oAuth2Attributes);

        return new CustomIntegratedUser(member, oAuth2Attributes);
    }

    private Member loadOrSaveUser(final String oAuth2LoginId, final OAuth2Attributes oAuth2Attributes) {
        Optional<Member> loadUser = memberRepository.findByLoginId(LoginId.from(oAuth2LoginId));

        return loadUser.orElseGet(() ->
                memberRepository.save(oAuth2Attributes.toEntity(oAuth2LoginId, oAuth2Attributes.getOAuth2UserInfo())));
    }

    private SocialType getSocialType(final String registrationId) {
        if ("kakao".equals(registrationId))
            return SocialType.KAKAO;
        return SocialType.GOOGLE;
    }
}
