package com.knk.refrigerator_manager.Login;

import com.knk.refrigerator_manager.user.LoginType;
import com.knk.refrigerator_manager.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class RegisterForm {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;

    @Builder
    public RegisterForm(Long id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User toEntity(){
        return User.builder()
                .id(id)
                .username(username)
//                .password(password)
                .password(new BCryptPasswordEncoder().encode(password))
                .email(email)
                .role(role)
                .phone("0000")
                .birth(new Date())
                .enroll_date(LocalDateTime.now())
                .login_type(LoginType.KAKAO)
                .build();
    }

}
