package com.fourth.neulbom.dto;

import com.fourth.neulbom.entity.User;
import com.fourth.neulbom.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String name;
    private String nickname;
    private String email;
    private UserRole role;

    public static UserResponseDto from(User result) {
        return new UserResponseDto(result.getId(),
                result.getName(),
                result.getNickname(),
                result.getEmail(),
                result.getRole()
                );
    }
}
