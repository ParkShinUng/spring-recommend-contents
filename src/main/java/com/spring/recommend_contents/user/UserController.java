package com.spring.recommend_contents.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "sign_in_form";
    }

    @GetMapping("/signup")
    public String signUp(UserSignUpForm userSignUpForm) {
        return "sign_up_form";
    }

    @PostMapping("/signup")
    public String signUp(@Valid UserSignUpForm userSignUpForm, HttpSession session,
                         @RequestParam(value="email") String userEmail, Model model,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign_up_form";
        }

        if (!userSignUpForm.getPassword().equals(userSignUpForm.getConfirmPassword())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "입력하신 비밀번호가 일치하지 않습니다.");
            return "sign_up_form";
        }

        Boolean isEmailVerified = (Boolean) session.getAttribute("isEmailVerified");
        if (isEmailVerified == null || !isEmailVerified) {
            model.addAttribute("errorMessage", "이메일 인증이 완료되지 않았습니다.");
            return "sign_up_form";
        }

        try {
            this.userService.create(userSignUpForm.getEmail(), userSignUpForm.getPassword());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.rejectValue("signupFailed", "이미 등록된 사용자입니다.");
            return "sign_up_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.rejectValue("signupFailed", e.getMessage());
            return "sign_up_form";
        }

        session.removeAttribute("isEmailVerified");
        session.removeAttribute("verifiedEmail");

        // TODO : 회원가입 완료 문구 출력 구현
        return "redirect:/user/login";
    }
}
