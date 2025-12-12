package com.fourth.neulbom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemRequestDto {
    private String unit;
    private String type;
    private String difficulty;
    private int count;
}
