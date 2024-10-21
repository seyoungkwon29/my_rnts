package com.zerob.my_rnts.global.jwt;

import com.zerob.my_rnts.domain.member.vo.Mail;
import com.zerob.my_rnts.global.jwt.exception.TokenErrorCode;
import com.zerob.my_rnts.global.jwt.exception.TokenException;
import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
import com.zerob.my_rnts.global.security.principal.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "role";
    private final String secretKey;
    private final Long accessTokenExpiration;
    private final CustomUserDetailsService customUserDetailsService;

    private SecretKey key;

    public TokenProvider(@Value("${jwt.secretKey}") String secretKey,
                         @Value("${jwt.access.expiration}") Long accessTokenExpiration,
                         CustomUserDetailsService customUserDetailsService) {
        this.secretKey = secretKey;
        this.accessTokenExpiration = accessTokenExpiration;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String loginId, Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return generateToken(loginId, authorities, accessTokenExpiration);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token).getPayload();

        List<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String loginId = String.valueOf(claims.get("id"));

        CustomIntegratedUser principal = (CustomIntegratedUser) customUserDetailsService.loadUserByUsername(loginId);

        return new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(this.key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
            throw new TokenException(TokenErrorCode.TOKEN_NOT_FOUND);
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
            throw new TokenException(TokenErrorCode.TOKEN_EXPIRED);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
            throw new TokenException(TokenErrorCode.TOKEN_INVALID);
        }
    }

    public String generateToken(String loginId, String authorities, Long expiration) {
        long expirationTime = (new Date()).getTime() + expiration;
        return Jwts.builder()
                .claim("id", loginId)
                .claim(AUTHORITIES_KEY, authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(expirationTime))
                .signWith(this.key, Jwts.SIG.HS512)
                .compact();
    }

    public String generateTokenForMail(Mail mail) {
        long expirationTime = (new Date()).getTime() + 1000 * 60 * 15;
        return Jwts.builder()
                .claim("mail", mail.mail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(expirationTime))
                .signWith(this.key, Jwts.SIG.HS512)
                .compact();
    }

    public Claims getMailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token).getPayload();
    }

}