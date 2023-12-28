package com.irembo.accountmanagement.repository;

import com.irembo.accountmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAll();
    Optional<UserEntity> findByUserId(Integer id);
    List<UserEntity> findByStatus(Integer status);
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByUserNameAndPassword(String userName, String password);
}
