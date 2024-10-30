//package com.zerob.my_rnts.domain.member;
//
//import com.zerob.my_rnts.domain.member.domain.Member;
//import com.zerob.my_rnts.domain.member.vo.*;
//import com.zerob.my_rnts.global.oauth2.userInfo.CustomIntegratedUser;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithSecurityContextFactory;
//
//import java.util.List;
//
//public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {
//
//    @Override
//    public SecurityContext createSecurityContext(WithAuthUser annotation) {
//        Member member = Member.builder()
//                .id(annotation.id())
//                .loginId(LoginId.from(annotation.loginId()))
//                .password(Password.from(annotation.password()))
//                .mail(Mail.from(annotation.mail()))
//                .nickname(Nickname.from(annotation.nickname()))
//                .role(annotation.role())
//                .build();
//
//        // 사용자 정보
//        CustomIntegratedUser userDetails = new CustomIntegratedUser(member);
//
//        // 권한 부여
//        List<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(RoleType.USER.name());
////
////        SecurityContextHolder.getContext().setAuthentication(
////                new UsernamePasswordAuthenticationToken(userDetails, annotation.password(), authorities));
////
////        return SecurityContextHolder.getContext();
////    }
//        // Security Context에 인증 정보 설정
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, annotation.password(), authorities));
//        SecurityContextHolder.setContext(context);
//
//        return context;
//    }
//}
