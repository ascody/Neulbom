package com.fourth.neulbom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private boolean verified;
    private LocalDateTime lastLogin;
    private LocalDateTime verificationExpiry;

    private UserRole role;

    private String school;
    private String inviteCode;

    @Builder
    public User(String name,
                String nickname,
                String email,
                String password,
                UserRole role,
                Boolean verified,
                LocalDateTime verificationExpiry,
                String inviteCode) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verified = verified;
        this.verificationExpiry = verificationExpiry;
        this.inviteCode = inviteCode;
    }

    public void markVerified() {
        this.verified = true;
        renewVerificationExpiry();
    }

    public void renewVerificationExpiry() {
        this.verificationExpiry = LocalDateTime.now().plusMonths(6);
    }

    public boolean isVerificationExpired() {
        return verificationExpiry != null && verificationExpiry.isBefore(LocalDateTime.now());
    }

    public void markLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }
}


