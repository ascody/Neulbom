package com.fourth.neulbom.service;

import com.fourth.neulbom.dto.AssignmentDto;
import com.fourth.neulbom.dto.AssignmentResponseDto;
import com.fourth.neulbom.entity.Assignment;
import com.fourth.neulbom.entity.User;
import com.fourth.neulbom.repository.AssignmentRepository;
import com.fourth.neulbom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public List<AssignmentResponseDto> getAssignment(String inviteCode) {
        User user = userRepository.getUserByInviteCode(inviteCode);

        if (user == null) {
            throw new IllegalArgumentException("해당 초대 코드에 해당하는 사용자가 없습니다. inviteCode=" + inviteCode);
        }

        List<Assignment> assignments = assignmentRepository.findAllByUser_Id(user.getId());

        List<AssignmentResponseDto> result = new ArrayList<>();
        return assignments.stream().map(
                a -> new AssignmentResponseDto(
                        a.getId(),
                        a.getUnit(),
                        a.getType(),
                        a.getProblems(),
                        a.getExpirationDate()
                )
        ).toList();
    }
}
