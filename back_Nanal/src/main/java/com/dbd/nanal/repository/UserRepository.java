package com.dbd.nanal.repository;

import com.dbd.nanal.model.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByUserIdx(int userIdx);

}

