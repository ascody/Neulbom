package com.fourth.neulbom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProblemValidationResultDto {
    private List<ProblemValidateDto> problems;
    private List<ProblemValidateDto> failures;
}
