package com.dbd.nanal.repository;

import com.dbd.nanal.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    UserEntity findByUserId(String userId);
    UserEntity findByEmail(String email);
    UserEntity findByUserIdx(int userIdx);
    UserEntity findByUserIdAndPassword(String userId, String password);

    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);

//    List<UserEntity> findByUserIdContaining(String userId);

    @Query("select a from UserEntity a where a.userId like concat('%', :userId, '%') and a.userIdx not in (select f.user_idx1.userIdx from FriendEntity f where f.user_idx2.userIdx=:userIdx) and not(a.userIdx=:userIdx)")
    List<UserEntity> searchUserId(String userId, int userIdx);


}

