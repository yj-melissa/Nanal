package com.dbd.nanal.repository;

import com.dbd.nanal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    UserEntity findByUserId(String userId);

    UserEntity findByEmail(String email);

    UserEntity findByUserIdx(int userIdx);

    Boolean existsByUserId(String userId);

    Boolean existsByEmail(String email);

    @Query("select a from UserEntity a where a.userId like concat('%', :userId, '%') and a.userIdx not in (select f.user_idx1.userIdx from FriendEntity f where f.user_idx2.userIdx=:userIdx) and not(a.userIdx=:userIdx)")
    List<UserEntity> searchUserId(@Param("userId") String userId, @Param("userIdx") int userIdx);
}

