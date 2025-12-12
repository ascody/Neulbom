package com.fourth.neulbom.controller;

import com.fourth.neulbom.dto.AssignmentResponseDto;
import com.fourth.neulbom.repository.AssignmentRepository;
import com.fourth.neulbom.repository.UserRepository;
import com.fourth.neulbom.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/assignments")
@RequiredArgsConstructor
@RestController
public class AssignmentController {
    private final AssignmentService assignmentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AssignmentResponseDto> getAssignment(@RequestParam String inviteCode) {
        return assignmentService.getAssignment(inviteCode);
    }
}
