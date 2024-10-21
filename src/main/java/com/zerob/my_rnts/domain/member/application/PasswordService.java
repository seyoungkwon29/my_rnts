package com.zerob.my_rnts.domain.member.application;

import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.exception.MemberErrorCode;
import com.zerob.my_rnts.domain.member.exception.MemberException;
import com.zerob.my_rnts.domain.member.repository.MemberRepository;
import com.zerob.my_rnts.domain.member.vo.Mail;
import com.zerob.my_rnts.global.jwt.TokenProvider;
import com.zerob.my_rnts.global.mail.exception.MailErrorCode;
import com.zerob.my_rnts.global.mail.exception.MailException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String requestPasswordReset(final boolean isVerified, final String mail) {
        if (!isVerified) throw new MailException(MailErrorCode.VERIFICATION_FAILED);
        return tokenProvider.generateTokenForMail(Mail.from(mail));
    }

    public void changePassword(final String token, final Member resetMember) {
        tokenProvider.validateToken(token);

        String mail = (String) tokenProvider.getMailFromToken(token).get("mail");
        Member member = memberRepository.findByMail(Mail.from(mail))
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (passwordEncoder.matches(resetMember.getPassword().password(), member.getPassword().password()))
            throw new MemberException(MemberErrorCode.DUPLICATED_PASSWORD);
        else
            member.changePassowrd(resetMember, passwordEncoder);
    }
}
