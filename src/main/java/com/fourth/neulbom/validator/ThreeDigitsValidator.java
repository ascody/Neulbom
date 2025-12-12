package com.fourth.neulbom.validator;

import com.fourth.neulbom.dto.ProblemDto;
import com.fourth.neulbom.dto.ProblemValidateDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ThreeDigitsValidator implements ProblemValidator {
    @Override
    public boolean supports(String unit, String type) {
        return unit.equals("몇 백의 덧셈과 뺄셈");
    }

    @Override
    public ProblemValidateDto validate(ProblemDto problem) {
        String type = problem.getType();
        return switch (type) {
            case "두 덧셈,뺄셈 수 비교" -> validateCompare(problem);
            case "두 덧셈 계산", "문장제" -> validateAddAndSum(problem);
            case "복합 연산" -> validateComplex(problem);
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

    private ProblemValidateDto validateCompare(ProblemDto problem) {
        Map<String, Integer> vars = problem.getVariables();
        Integer a = vars.get("a");
        Integer b = vars.get("b");
        Integer c = vars.get("c");
        Integer d = vars.get("d");

        int answer = problem.getAnswer();
        int left = a + b;
        int right = c + d;

        boolean ok = (left > right) == (answer == 0);
        if (ok) {
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
                "비교 결과가 다릅니다."
        );
    }

    private ProblemValidateDto validateAddAndSum(ProblemDto problem) {
        Map<String, Integer> vars = problem.getVariables();
        Integer a = vars.get("a");
        Integer b = vars.get("b");

        int answer = problem.getAnswer();

        int result = a + b;
        boolean ok = (result == answer);
        if (ok) {
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

    private ProblemValidateDto validateComplex(ProblemDto problem) {
        Map<String, Integer> vars = problem.getVariables();
        String level = problem.getLevel();
        int answer = problem.getAnswer();

        if (!level.equals("상")) {
            return new ProblemValidateDto(
                    problem.getUnit(),
                    problem.getType(),
                    problem.getLevel(),
                    problem.getVariables(),
                    problem.getScript(),
                    problem.getAnswer(),
                    false,
                    "난이도가 잘못 설정되었습니다."
            );
        }

        Integer a = vars.get("a");
        Integer b = vars.get("b");
        Integer c = vars.get("c");

        int result = a + b - c;

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
