package com.fourth.neulbom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ProblemDto {
    private Integer id;
    private String unit;
    private String type;
    private String level;
    private Map<String, Integer> variables;
    private String script;
    private Integer answer;

    public ProblemDto(String unit, String type, String level, Map<String, Integer> variables, String script, Integer answer) {
        this.unit = unit;
        this.type = type;
        this.level = level;
        this.variables = variables;
        this.script = script;
        this.answer = answer;
    }
}
