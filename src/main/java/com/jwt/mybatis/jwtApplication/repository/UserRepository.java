package com.jwt.mybatis.jwtApplication.repository;

import com.jwt.mybatis.jwtApplication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByUsername(String username);
}
