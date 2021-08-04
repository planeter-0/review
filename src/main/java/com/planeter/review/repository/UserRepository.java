package com.planeter.review.repository;

import com.planeter.review.model.entity.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByUsername(String username);

    boolean existsByUsername(String username);
}
