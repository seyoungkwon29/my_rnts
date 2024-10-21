package com.zerob.my_rnts.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerob.my_rnts.domain.member.dto.LoginRequest;
import com.zerob.my_rnts.domain.member.dto.SignUpRequest;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
    public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

        private final AuthenticationManager authenticationManager;

        @SneakyThrows
        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            try {
                // 일반 로그인 (소셜 로그인은 OAuth2LoginAuthenticationFilter에 자동 적용)
                // 넘어오는 데이터가 JSON 형태이면 이를 변환하는 과정이 필요
                ServletInputStream servletInputStream = request.getInputStream();
                String body = StreamUtils.copyToString(servletInputStream, StandardCharsets.UTF_8);

                log.info("Method : {}", request.getMethod());
                log.info("RequestURI : {}", request.getRequestURI());

                // JSON -> Java 객체
                ObjectMapper objectMapper = new ObjectMapper();
                LoginRequest loginRequest = objectMapper.readValue(body, LoginRequest.class);
                String loginId = loginRequest.getLoginId().loginId();
                String password = loginRequest.getPassword().password();

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);

                return authenticationManager.authenticate(authenticationToken);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
