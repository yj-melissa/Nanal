package com.dbd.nanal.repository;

import com.dbd.nanal.model.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Integer> {
    @Query("select f from FriendEntity f where f.user_idx1.userIdx = :userIdx")
    List<FriendEntity> findAllFriends(@Param("userIdx") int userIdx);
}
