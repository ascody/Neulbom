package com.fourth.neulbom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fourth.neulbom.entity.User;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ProblemSaveDto {
    private Integer id;
    private String unit;
    private String type;
    private String level;
    private Map<String, Integer> variables;
    private String script;
    private Integer answer;
    private User user;
}
