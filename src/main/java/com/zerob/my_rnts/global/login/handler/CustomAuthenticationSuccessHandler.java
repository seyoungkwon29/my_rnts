package com.zerob.my_rnts.global.login.handler;

import com.zerob.my_rnts.global.jwt.TokenProvider;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.header}")
    private String HEADER;
    private final TokenProvider tokenProvider;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomIntegratedUser user = (CustomIntegratedUser) authentication.getPrincipal();

        String accessToken = tokenProvider.createToken(user.getUsername(), authentication);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setHeader(HEADER, "Bearer " + accessToken);

        log.info("{}", "Bearer " + accessToken);

        // 리디렉션 URL 구성
        String redirectUrl = UriComponentsBuilder.fromUriString("https://www.rnts.o-r.kr")
                .queryParam(HEADER, accessToken)
                .build().toUriString();

        // 리디렉션 수행
        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }
}
