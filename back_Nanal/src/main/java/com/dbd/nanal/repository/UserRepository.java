package com.dbd.nanal.repository;

import com.dbd.nanal.model.UserEntity;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    UserEntity findByUserId(String userId);
    UserEntity findByEmail(String email);
    UserEntity findByUserIdx(int userIdx);
    UserEntity findByUserIdAndPassword(String userId, String password);

    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);

}

