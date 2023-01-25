package com.dbd.nanal.repository;

import com.dbd.nanal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    UserEntity findByUserId(String userId);
    UserEntity findByEmail(String email);
    UserEntity findByUserIdx(int userIdx);
    UserEntity findByUserIdAndUserPassword(String userId, String userPassword);

    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);

}

