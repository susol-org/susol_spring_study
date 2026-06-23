package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserEmailId(String userEmailId);

    boolean existsByUserEmailId(String userEmailId);
}
