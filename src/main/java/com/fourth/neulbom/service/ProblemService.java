package com.fourth.neulbom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fourth.neulbom.dto.ProblemValidateDto;
import com.fourth.neulbom.entity.Assignment;
import com.fourth.neulbom.entity.Problem;
import com.fourth.neulbom.entity.User;
import com.fourth.neulbom.repository.AssignmentRepository;
import com.fourth.neulbom.repository.ProblemRepository;
import com.fourth.neulbom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProblemService {
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final AssignmentRepository assignmentRepository;

    public void saveProblem(@RequestBody List<ProblemValidateDto> request) throws JsonProcessingException {
        List<Problem> problems = new ArrayList<>();

        for (ProblemValidateDto pv : request) {
            Problem problem = Problem.builder()
                    .unit(pv.getUnit())
                    .type(pv.getType())
                    .level(pv.getLevel())
                    .variables(pv.getVariables())
                    .script(pv.getScript())
                    .answer(pv.getAnswer())
                    .user(userRepository.getUserById(2))
                    .build();
            problems.add(problem);
            problemRepository.save(problem);
        }

        User user = userRepository.getUserById(2);
        Assignment assignment = Assignment.builder()
                .user(user)
                .inviteCode(user.getInviteCode())
                .unit(problems.getFirst().getUnit())
                .type(problems.getFirst().getType())
                .problems(problems)
                .expirationDate(LocalDateTime.now().plusDays(7))
                .build();
        assignmentRepository.save(assignment);
    }
}
