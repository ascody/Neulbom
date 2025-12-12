package com.fourth.neulbom.validator;

import com.fourth.neulbom.dto.ProblemDto;
import com.fourth.neulbom.dto.ProblemValidateDto;

import java.util.Map;

public interface ProblemValidator {
    boolean supports(String unit, String type);
    ProblemValidateDto validate(ProblemDto problem);
}
