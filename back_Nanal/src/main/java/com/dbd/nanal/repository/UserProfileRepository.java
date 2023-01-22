package com.dbd.nanal.repository;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

    Optional<UserProfileEntity> findByUser(UserEntity user);
    Optional<UserProfileEntity> findByNickname(String Nickname);


}

