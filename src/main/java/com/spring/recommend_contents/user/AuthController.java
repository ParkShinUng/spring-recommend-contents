package com.spring.recommend_contents.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final RedisTemplate<String, String> redisTemplate;
    private final JavaMailSender mailSender;

    private static final long EMAIL_CODE_TTL_SECONDS = 180; // 3분

    @PostMapping("/send-code")
    public Map<String, Object> sendAuthCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Map<String, Object> response = new HashMap<>();

        try {
            String authCode = generateAuthCode();
            redisTemplate.opsForValue().set("emailAuth:" + email, authCode, EMAIL_CODE_TTL_SECONDS, TimeUnit.SECONDS);

            sendEmail(email, authCode);

            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "이메일 전송 실패: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    @PostMapping("/verify-code")
    public Map<String, Object> verifyCode(@RequestBody Map<String, String> request, HttpSession session) {
        String email = request.get("email");
        String inputCode = request.get("code");

        String storedCode = redisTemplate.opsForValue().get("emailAuth:" + email);
        Map<String, Object> response = new HashMap<>();

        if (storedCode == null) {
            response.put("success", false);
            response.put("message", "인증 코드가 만료되었거나 존재하지 않습니다.");
            return response;
        }

        if (storedCode.equals(inputCode)) {
            // 인증 성공
            redisTemplate.delete("emailAuth:" + email); // 일회용이므로 삭제

            // 세션에 인증 성공 플래그 및 이메일 저장
            session.setAttribute("isEmailVerified", true);
            session.setAttribute("verifiedEmail", email);

            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("message", "인증 코드가 올바르지 않습니다.");
        }

        return response;
    }

    private String generateAuthCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000); // 6자리 숫자
    }

    private void sendEmail(String to, String code) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage.setFrom("${GMAIL_APP_EMAIL}");
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, to);
            mimeMessage.setSubject("[인증 코드] Feelight 이메일 확인을 위한 인증 코드입니다.");
            String body = "";
            body += "<h3>" + "Feelight 인증 번호" + "</h3>";
            body += "<h1>" + code + "</h1>";
            body += "<h3>" + "해당 코드는 3분간 유효합니다." + "</h3>";
            body += "<h3>" + "감사합니다." + "</h3>";
            mimeMessage.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }
}