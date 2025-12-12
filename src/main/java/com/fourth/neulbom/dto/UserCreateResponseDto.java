package com.fourth.neulbom.dto;

import com.fourth.neulbom.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserCreateResponseDto {
    private Integer id;
    private String email;
    private String name;

    public static UserCreateResponseDto from(User user) {
        return new UserCreateResponseDto(user.getId(), user.getEmail(), user.getName());
    }
}
