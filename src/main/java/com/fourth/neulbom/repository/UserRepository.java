package com.fourth.neulbom.repository;

import com.fourth.neulbom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByName(String name);
    User getUserById(Integer id);
    User getUserByInviteCode(String code);
}
