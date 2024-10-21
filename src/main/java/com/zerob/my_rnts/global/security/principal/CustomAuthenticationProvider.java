package com.zerob.my_rnts.global.security.principal;

import com.zerob.my_rnts.domain.member.vo.LoginId;
import com.zerob.my_rnts.domain.member.vo.Password;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        LoginId loginId = LoginId.from(authentication.getName());
        Password password = Password.from(authentication.getCredentials().toString());

        CustomIntegratedUser principal = (CustomIntegratedUser) customUserDetailsService.loadUserByUsername(loginId.loginId());

        if (!passwordEncoder().matches(password.password(), principal.getPassword())) {
            throw new BadCredentialsException("Provider - authenticate() : 비밀번호가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
