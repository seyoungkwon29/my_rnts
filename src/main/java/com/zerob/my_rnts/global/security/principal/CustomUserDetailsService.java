package com.zerob.my_rnts.global.security.principal;

import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.repository.MemberRepository;
import com.zerob.my_rnts.domain.member.vo.LoginId;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(LoginId.from(loginId))
                .orElseThrow(() -> new UsernameNotFoundException("loadUserByUsername - 사용자를 찾을 수 없습니다."));
        return new CustomIntegratedUser(member);
    }
}