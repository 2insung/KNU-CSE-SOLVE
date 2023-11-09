package com.project.web.service.auth;

import com.project.web.domain.SignUpCode;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.SignUpCodeRepository;
import com.project.web.repository.member.MemberAuthRepository;
import com.project.web.repository.member.MemberDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SignUpEmailService {
    private final JavaMailSender javaMailSender;
    private final SignUpCodeRepository signUpCodeRepository;
    private final MemberAuthRepository memberAuthRepository;
    private final MemberDetailRepository memberDetailRepository;

    @Value("${spring.mail.username}")
    private String id;
    private final String ePw = createKey();

    public MimeMessage createEmail(String to) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("회원가입 인증 코드");

        String msg = "";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += ePw;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html");
        message.setFrom(new InternetAddress(id, "이인성"));

        return message;
    }

    public String sendEmail(String username) throws Exception {
        MimeMessage message = createEmail(username);
        try {
            javaMailSender.send(message);
            if (!signUpCodeRepository.existsByUsername(username)) {
                SignUpCode signUpCode = SignUpCode.builder()
                        .username(username)
                        .code(ePw)
                        .build();
                signUpCodeRepository.save(signUpCode);
            }
            else {
                SignUpCode signUpCode = signUpCodeRepository.findByUsername(username)
                        .orElseThrow(() -> new Error404Exception("등록되지 않았습니다."));
                signUpCode.updateCode(ePw);
            }
        }
        catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }

    @Transactional(readOnly = true)
    public Boolean checkCode(String email, String code) {
        SignUpCode signUpCode = signUpCodeRepository.findByUsername(email)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 이메일입니다."));
        return code.equals(signUpCode.getCode());
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            key.append((random.nextInt(10)));
        }
        return key.toString();
    }
}
