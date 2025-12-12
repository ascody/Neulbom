package com.fourth.neulbom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourth.neulbom.dto.ProblemValidateDto;
import com.fourth.neulbom.dto.ProblemValidationResultDto;
import com.fourth.neulbom.mcp.ProblemTools;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ProblemGenerationService {
    private final ObjectMapper objectMapper;
    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = """
        당신은 특수학교 중학생(일반학교 초등 2~3학년 수준)을 위한 수학 문제 생성기입니다.
        이 대화에서는 MCP 도구(typeLookup, problemValidation 등)를 1번만 호출하세요.
        문제 1개를 직접 생성한 뒤, 그 문제 정보를 JSON 한 개 객체로만 반환합니다.
        
        규칙:
        - 문제는 1개만 생성합니다.
        - 모든 출력은 JSON 한 개 객체여야 하며, JSON 앞뒤에 다른 텍스트를 붙이지 않습니다.
        - 모든 문제 스크립트 내 변수는 반드시 중괄호로 감쌉니다. (예: {a}, {b}, {c})
        - "variables" 객체의 키는 script 내 중괄호 변수({a}, {b}, ...)와 정확히 일치해야 합니다.
        - answer 필드를 반드시 포함하며, 실제 계산 결과여야 합니다.
        - 검증 여부와 상관없이, 첫 번째로 생성한 문제를 그대로 반환합니다. 문제를 재생성하거나 다시 검증하지 않습니다.
        
        추가 규칙(곱셈의 기초 관련):
        - 곱셈 문제에서 변수 a, b는 1 이상 9 이하의 정수여야 합니다.
        - problemValidation에서 변경된 변수와 답을 사용하세요.
        
        출력 형식 예시:
        {
          "unit": "곱셈의 기초",
          "type": "문장제",
          "level": "상",
          "variables": { "a": 5, "b": 3 },
          "script": "{a}개의 사과가 {b}명에게 있습니다. 모두 몇 개의 사과가 있나요?",
          "answer": 15,
          "ok": true,
          "reason": "이상 없음"
        }
        """;
    @Getter
    private final ProblemTools problemTools;

    public ProblemGenerationService(ObjectMapper objectMapper, ChatClient chatClient, ProblemTools problemTools) {
        this.objectMapper = objectMapper;
        this.chatClient = chatClient;
        this.problemTools = problemTools;
    }

    public ProblemValidationResultDto generate(String unit, String type, String level, int count) throws JsonProcessingException {

        List<ProblemValidateDto> problems =  new ArrayList<>();
        List<ProblemValidateDto> failures =  new ArrayList<>();

        int limit = 0;
        while (problems.size() < count && limit < 5) {
            ProblemValidateDto result = callLLM(unit, type, level);

            log.info("🌀 [GENERATE LOOP] iter={}, problems={}, failures={}, resultOk={}",
                    limit,
                    problems.size(),
                    failures.size(),
                    (result != null ? result.isOk() : null)
            );


            if (result.isOk()) {
                problems.add(result);
            }  else {
                failures.add(result);
                limit++;
            }
        }

        return new ProblemValidationResultDto(problems, failures);
    }
    private ProblemValidateDto callLLM(String unit, String type, String level) {
        String prompt = String.format("""
            아래 조건에 따라 문제를 생성하세요.
            단원(unit): %s
            유형(type): %s
            난이도(level): %s

            - 각 문제는 무작위 템플릿을 기반으로 생성하세요.
            - 각 문제는 위 템플릿 목록 중 무작위로 하나를 선택하여 생성하세요.
            - answer 필드를 반드시 포함하고, 실제 계산 결과여야 합니다.
            - 출력은 반드시 JSON 형식이어야 합니다.
            """, unit, type, level);

        try {
            String response = chatClient.prompt()
                    .system(SYSTEM_PROMPT)
                    .user(prompt)
                    .call()
                    .content();

            log.info("🧩 [LLM RAW RESPONSE START]\n{}\n🧩 [LLM RAW RESPONSE END]", response);

            return objectMapper.readValue(response, ProblemValidateDto.class);
        } catch (Exception e) {
            System.err.println("LLM 호출 또는 JSON 파싱 실패: " + e.getMessage());

            ProblemValidateDto fail = new ProblemValidateDto();
            fail.setUnit(unit);
            fail.setType(type);
            fail.setLevel(level);
            fail.setVariables(null);
            fail.setScript(null);
            fail.setAnswer(null);
            fail.setOk(false);
            fail.setReason("LLM 호출 또는 JSON 파싱 실패: " + e.getMessage());

            return fail;
        }
    }
}
