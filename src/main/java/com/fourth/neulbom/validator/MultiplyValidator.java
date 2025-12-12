package com.fourth.neulbom.validator;

import com.fourth.neulbom.dto.ProblemDto;
import com.fourth.neulbom.dto.ProblemValidateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class MultiplyValidator implements ProblemValidator {
    @Override
    public boolean supports(String unit, String type) {
        return unit.equals("곱셈의 기초");
    }

    @Override
    public ProblemValidateDto validate(ProblemDto problem) {
        log.info("🧩 [GEN RAW ProblemDto]\n{}\n", problem);

        String type = problem.getType();
        return switch (type) {
            case "구구단", "문장제" -> validateMultiply(problem);
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

    private ProblemValidateDto validateMultiply(ProblemDto problem) {
        Map<String, Integer> variables = problem.getVariables();

        log.info("🧩 [VALIDATE START]");
        log.info("unit={}, type={}, level={}", problem.getUnit(), problem.getType(), problem.getLevel());
        log.info("variables={}, answer={}", problem.getVariables(), problem.getAnswer());

        if (variables == null) {
            log.info("🧩 [VALIDATE RESULT]");
            log.info("ok={}, reason={}", false, "변수(variables)가 비어 있습니다.");

            return new ProblemValidateDto(
                    problem.getUnit(),
                    problem.getType(),
                    problem.getLevel(),
                    problem.getVariables(),
                    problem.getScript(),
                    problem.getAnswer(),
                    false,
                    "변수(variables)가 비어 있습니다."
            );
        }

        Integer a = variables.get("a");
        Integer b = variables.get("b");

//        if (a > 9 || b > 9) {
//            if (a > 9) a = ThreadLocalRandom.current().nextInt(1, 10);
//            if (b > 9) b = ThreadLocalRandom.current().nextInt(1, 10);;
//
//            Map<String, Integer> vars = new HashMap<>();
//            vars.put("a", a);
//            vars.put("b", b);
//
//            problem.setVariables(vars);
//
//            problem.setAnswer(a * b);
//        }

        a = ThreadLocalRandom.current().nextInt(1, 10);
        b = ThreadLocalRandom.current().nextInt(1, 10);;

        Map<String, Integer> vars = new HashMap<>();
        vars.put("a", a);
        vars.put("b", b);

        problem.setVariables(vars);

        problem.setAnswer(a * b);

        Integer answer = problem.getAnswer();

        if (a == null || b == null || answer == null) {
            log.info("🧩 [VALIDATE RESULT]");
            log.info("ok={}, reason={}", false, "변수 a, b 또는 answer 중 누락된 값이 있습니다.");

            return new ProblemValidateDto(
                    problem.getUnit(),
                    problem.getType(),
                    problem.getLevel(),
                    problem.getVariables(),
                    problem.getScript(),
                    problem.getAnswer(),
                    false,
                    "변수 a, b 또는 answer 중 누락된 값이 있습니다."
            );
        }

        int result = a * b;

        log.info("🧩 [VALIDATE RESULT]");
        log.info("ok={}, reason={}", true, "이상없음.");
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

        log.info("🧩 [VALIDATE RESULT]");
        log.info("ok={}, reason={}", false, "정답과 다릅니다");

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
