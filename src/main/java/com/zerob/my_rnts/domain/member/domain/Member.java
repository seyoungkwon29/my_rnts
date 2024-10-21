package com.zerob.my_rnts.domain.member.domain;

import com.zerob.my_rnts.domain.member.vo.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;

    private LoginId loginId;

    private Password password;

    private Mail mail;

    private Nickname nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Tendency tendency;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    // KAKAO, NAVER, GOOGLE
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    private AgreeTerms agreeTerms;

    @Builder
    private Member(Long id, LoginId loginId, Password password, Mail mail, Nickname nickname, String profileImage,
                   Tendency tendency, RoleType role, SocialType socialType, String socialId, AgreeTerms agreeTerms) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.mail = mail;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.tendency = tendency;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        this.agreeTerms = agreeTerms;
    }

    public Member encode(final PasswordEncoder passwordEncoder) {
        password = Password.encode(password.password(), passwordEncoder);
        return this;
    }

    public void update(final Member member) {
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImage();
    }

    public void updateTendency(final Member member) {
        this.tendency = member.getTendency();
    }

    public void changePassowrd(final Member resetMember, final PasswordEncoder passwordEncoder) {
        this.password = Password.encode(resetMember.getPassword().password(), passwordEncoder);
    }
}
