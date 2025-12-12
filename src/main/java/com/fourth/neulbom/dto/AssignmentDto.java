package com.fourth.neulbom.dto;

import com.fourth.neulbom.entity.Problem;
import com.fourth.neulbom.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentDto {
    private Integer id;
    private User user;
    private String inviteCode;
    private String unit;
    private String type;
    private List<Problem> problems = new ArrayList<>();
    private LocalDateTime expirationDate;
}
