package com.fourth.neulbom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fourth.neulbom.dto.*;
import com.fourth.neulbom.entity.User;
import com.fourth.neulbom.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserCreateResponseDto create(UserCreateRequestDto request){

        User user = User.builder()
                .name(request.getName())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .verified(true)
                .verificationExpiry(LocalDateTime.now().plusMonths(6))
                .inviteCode(UUID.randomUUID().toString().replace("-", "").substring(0, 8))
                .build();

        User saved = userRepository.save(user);

        return UserCreateResponseDto.from(saved);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserData(Integer id) throws JsonProcessingException {
        User user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("user not found: " + id));
        return UserResponseDto.from(user);
    }

    @Transactional
    public UserResponseDto update(Integer id, UserUpdateRequestDto request) throws JsonProcessingException {
        User user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("user not found: " + id));

        String oldName = user.getName();
        String oldNickname = user.getNickname();

        String newName = request.getName();
        String newNickname = request.getNickname();

        if (newName != null && !newName.equals(oldName)) {
            user.setName(newName);
        }

        if (newNickname != null && !newNickname.equals(oldNickname)) {
            user.setNickname(newNickname);
        }

        return UserResponseDto.from(user);
    }

    @Transactional
    public void delete(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("user not found: " + id));
        userRepository.delete(user);
    }

    @Transactional
    public void updatePassword(Integer id, PasswordUpdateRequestDto request) throws JsonProcessingException {
        User user =  userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("user not found: " + id));

        String password = user.getPassword();
        String currentPassword = request.getCurrentPassword();
        String newPassword = request.getNewPassword();
        String newPasswordConfirm = request.getNewPasswordConfirm();

        if (currentPassword == null || currentPassword.isBlank() ||
                newPassword == null || newPassword.isBlank() ||
                newPasswordConfirm == null || newPasswordConfirm.isBlank()) {

            throw new IllegalArgumentException("비밀번호를 모두 입력해야 합니다.");
        }

        if (!passwordEncoder.matches(currentPassword, password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if  (currentPassword.equals(newPassword)) {
            throw new IllegalArgumentException("새로운 비밀번호를 사용해주세요.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
    }
}
