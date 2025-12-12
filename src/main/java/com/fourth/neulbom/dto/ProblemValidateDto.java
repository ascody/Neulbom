package com.fourth.neulbom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemValidateDto {
    private String unit;
    private String type;
    private String level;
    private Map<String, Integer> variables;
    private String script;
    private Integer answer;
    private boolean ok;
    private String reason;
}
