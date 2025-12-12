package com.fourth.neulbom.repository;

import com.fourth.neulbom.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Integer> {
    List<Assignment> findAllByUser_Id(Integer id);
    List<Assignment> findAllByInviteCode(String code);
}
