package com.fourth.neulbom.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String unit;
    private String type;
    private String level;

    @ElementCollection
    @CollectionTable(name = "problem_variables", joinColumns = @JoinColumn(name = "problem_id"))
    @Column(name = "variable")
    private Map<String, Integer> variables;

    private String script;
    private int answer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Problem(String unit, String type, String level, Map<String, Integer> variables, String script, int answer,  User user) {
        this.unit = unit;
        this.type = type;
        this.level = level;
        this.variables = variables;
        this.script = script;
        this.answer = answer;
        this.user = user;
    }
}
