package com.spring.recommend_contents.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpForm {

    @NotEmpty(message = "Email 입력은 필수 항목입니다.")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호 입력은 필수 항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인 입력은 필수 항목입니다.")
    private String confirmPassword;
}
