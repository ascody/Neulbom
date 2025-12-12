package com.fourth.neulbom.dto;

import com.fourth.neulbom.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateRequestDto {
    private String name;
    private String nickname;
    private String email;
    private UserRole role;
    private String password;
}
