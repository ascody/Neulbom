package com.fourth.neulbom.validator;

import com.fourth.neulbom.dto.ProblemDto;
import com.fourth.neulbom.dto.ProblemValidateDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OnedigitValidator implements ProblemValidator {
    @Override
    public boolean supports(String unit, String type) {
        return unit.equals("받아올림이 있는 한 자리 수의 덧셈");
    }

    @Override
    public ProblemValidateDto validate(ProblemDto problem) {
        String type = problem.getType();
        return switch (type) {
            case "두 덧셈 계산", "문장제" -> validateAdd(problem);
            case "받아올림 유무 판별" -> validateCarry (problem);
            default -> new ProblemValidateDto(
                    problem.getUnit(),
                    problem.getType(),
                    problem.getLevel(),
                    problem.getVariables(),
                    problem.getScript(),
                    problem.getAnswer(),
                    false,
                    "알 수 없는 유형: " + type
            );
        };
    }

    private ProblemValidateDto validateAdd(ProblemDto problem) {
        Map<String, Integer> variables = problem.getVariables();
        Integer a = variables.get("a");
        Integer b = variables.get("b");

        String level = problem.getLevel();
        int answer = problem.getAnswer();

        int result = a + b;
        if (level.equals("상")) {
            Integer c = (Integer) variables.get("c");
            result += c;
        }


        if (result == answer) {
            return new ProblemValidateDto(
                    problem.getUnit(),
                    problem.getType(),
                    problem.getLevel(),
                    problem.getVariables(),
                    problem.getScript(),
                    problem.getAnswer(),
                    true,
                    "이상 없음"
            );
        }
        return new ProblemValidateDto(
                problem.getUnit(),
                problem.getType(),
                problem.getLevel(),
                problem.getVariables(),
                problem.getScript(),
                problem.getAnswer(),
                false,
                "정답과 다릅니다 - 정답: " + answer + ", 현재 답: " + result
        );
    }

    private ProblemValidateDto validateCarry(ProblemDto problem) {
        Map<String, Integer> variables = problem.getVariables();
        Integer a = variables.get("a");
        Integer b = variables.get("b");

        Integer answer = problem.getAnswer();
        int result = (a + b) >= 10 ? 1 : 0;

        if (result == answer) {
            return new ProblemValidateDto(
                    problem.getUnit(),
                    problem.getType(),
                    problem.getLevel(),
                    problem.getVariables(),
                    problem.getScript(),
                    problem.getAnswer(),
                    true,
                    "이상 없음"
            );
        }
        return new ProblemValidateDto(
                problem.getUnit(),
                problem.getType(),
                problem.getLevel(),
                problem.getVariables(),
                problem.getScript(),
                problem.getAnswer(),
                false,
                "정답과 다릅니다 - 정답: " + answer + ", 현재 답: " + result
        );
    }
}
