package com.zerob.my_rnts.domain.member.repository;


import com.zerob.my_rnts.domain.member.domain.Member;
import com.zerob.my_rnts.domain.member.vo.LoginId;
import com.zerob.my_rnts.domain.member.vo.Mail;
import com.zerob.my_rnts.domain.member.vo.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 일반 회원
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(final LoginId loginId);
    boolean existsByMail(final Mail mail);
    boolean existsByNickname(final Nickname nickname);

    Optional<Member> findByLoginId(final LoginId loginId);

    Optional<Member> findByMail(final Mail mail);
}
