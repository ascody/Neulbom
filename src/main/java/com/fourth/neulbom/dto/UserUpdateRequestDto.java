package com.fourth.neulbom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateRequestDto {
    private String name;
    private String nickname;
}
