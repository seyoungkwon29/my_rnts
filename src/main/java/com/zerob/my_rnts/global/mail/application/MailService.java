package com.zerob.my_rnts.global.mail.application;

import com.zerob.my_rnts.global.redis.RedisService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private static final String senderEmail = "seyoungkwon29@naver.com";
    private static final String AUTH_CODE_PREFIX = "AuthCode";
    private static final Duration DURATION = Duration.ofMillis(60000 * 5L);

    private String createCode() {
        Random random = new Random();
        int randomCode = 100000 + random.nextInt(900000);
        return String.valueOf(randomCode);
    }

    // 이메일 폼 생성
    private MimeMessage createMail(final String email, final String authCode) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("출발했어?! 인증번호입니다.");
        message.setFrom(senderEmail);
        message.setText(setMessage(authCode), "utf-8", "html");

        return message;
    }

    private String setMessage(final String authCode) {
        String message = "";
        message += "<h3>요청하신 인증 번호입니다.</h3>";
        message += "<h1>" + authCode + "</h1>";
        return message;
    }

    public void sendMail(final String to) throws MessagingException {
        String authCode = createCode();
        MimeMessage message = createMail(to, authCode);

        javaMailSender.send(message);
        redisService.setValues(AUTH_CODE_PREFIX + to, authCode, DURATION);

        log.info("authCode : {}", authCode);
    }

    public boolean verifiedCode(final String mail, final String authCode) {
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + mail);

        if (redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode)) {
            redisService.deleteValues(redisAuthCode);
            return true;
        } else if (!redisService.checkExistsValue(redisAuthCode)) {
            log.info("해당 키에 대한 값이 존재하지 않습니다.");
        } else if (!redisAuthCode.equals(authCode)) {
            log.info("인증코드가 일치하지 않습니다.");
        }
        return false;
    }
}