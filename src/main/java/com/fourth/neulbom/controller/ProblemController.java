package com.fourth.neulbom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fourth.neulbom.dto.ProblemRequestDto;
import com.fourth.neulbom.dto.ProblemValidateDto;
import com.fourth.neulbom.dto.ProblemValidationResultDto;
import com.fourth.neulbom.service.ProblemGenerationService;
import com.fourth.neulbom.service.ProblemService;
import com.fourth.neulbom.service.ProblemValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProblemController {

    private final ProblemGenerationService problemGenerationService;
    private final ProblemService problemService;

    public ProblemController(ProblemGenerationService problemGenerationService, ProblemService problemService) {
        this.problemGenerationService = problemGenerationService;
        this.problemService = problemService;
    }

    @PostMapping("/generate")
    public ProblemValidationResultDto generate(@RequestBody ProblemRequestDto request) throws JsonProcessingException {
        return problemGenerationService.generate(
                request.getUnit(),
                request.getType(),
                request.getDifficulty(),
                request.getCount()
        );
    }

    @PostMapping("/problem")
    @ResponseStatus(HttpStatus.OK)
    public void saveProblem(@RequestBody List<ProblemValidateDto> request) throws JsonProcessingException {
        problemService.saveProblem(request);
    }
}