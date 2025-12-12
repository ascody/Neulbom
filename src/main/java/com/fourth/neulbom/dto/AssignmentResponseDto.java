package com.fourth.neulbom.dto;

import com.fourth.neulbom.entity.Problem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentResponseDto {
    private Integer id;
    private String unit;
    private String type;
    private List<Problem> problems = new ArrayList<>();
    private LocalDateTime expirationDate;
}
