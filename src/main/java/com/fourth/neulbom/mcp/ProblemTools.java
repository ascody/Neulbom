package com.fourth.neulbom.mcp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourth.neulbom.dto.ProblemDto;
import com.fourth.neulbom.dto.ProblemValidateDto;
import com.fourth.neulbom.dto.SampleDto;
import com.fourth.neulbom.service.ProblemValidationService;
import com.fourth.neulbom.service.SampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ProblemTools {
    private final SampleService sampleService;
    private final ProblemValidationService validationService;

    public ProblemTools(SampleService sampleService, ProblemValidationService validationService) {
        this.sampleService = sampleService;
        this.validationService = validationService;
    }

    @Tool(
            name = "typeLookup",
            description = "단원과 유형에 맞는 문제 템플릿을 제공합니다."
    )
    public @JsonProperty("samples") List<SampleDto> lookup(String unit, String type) {
        return sampleService.getSamplesByUnitAndType(unit, type);
    }


    @Autowired
    private ObjectMapper objectMapper;

    @Tool(
            name = "problemValidation",
            description = "생성된 문제를 검수합니다."
    )
    public ProblemValidateDto validate(
            String unit,
            String type,
            String level,
            Map<String, Integer> variables,
            String script,
            Integer answer
    ) {
        ProblemDto problem = new ProblemDto(unit, type, level, variables, script, answer);
        return validationService.validate(problem);
    }
}
