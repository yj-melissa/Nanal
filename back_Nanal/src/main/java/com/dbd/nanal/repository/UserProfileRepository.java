package com.dbd.nanal.repository;

import com.dbd.nanal.model.UserProfileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Integer> {

//    UserProfileEntity findByUser(UserEntity user);
//    UserProfileEntity findByNickname(String nickname);
    UserProfileEntity findByProfileId(int profileId);
    Boolean existsByNickname(String nickname);



}

