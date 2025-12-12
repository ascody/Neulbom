package com.fourth.neulbom.service;

import com.fourth.neulbom.dto.ProblemDto;
import com.fourth.neulbom.dto.ProblemValidateDto;
import com.fourth.neulbom.validator.ProblemValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProblemValidationService {

    private final List<ProblemValidator> validators;
    public ProblemValidationService(List<ProblemValidator> validators) {
        this.validators = validators;
    }

    public ProblemValidateDto validate(ProblemDto problem) {
        log.info("🧩 Raw ProblemDto = {}", problem);

        String unit = problem.getUnit();
        String type = problem.getType();

        ProblemValidator validator = validators.stream()
                .filter(v -> v.supports(unit, type))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("단원과 유형을 찾을 수 없습니다. 단원: " + unit + ", 유형: " + type));

        return validator.validate(problem);
    }
}
