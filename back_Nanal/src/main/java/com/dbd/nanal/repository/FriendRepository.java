package com.dbd.nanal.repository;

import com.dbd.nanal.model.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Integer> {
    @Query("select f from FriendEntity f where f.user_idx1.userIdx = :userIdx")
    List<FriendEntity> findAllFriends(@Param("userIdx") int userIdx);

    @Query("select f from FriendEntity f where f.user_idx2.userIdx = :userIdx and f.user_idx1.userIdx not in (select g.user.userIdx from GroupUserRelationEntity g where g.groupDetail.groupIdx = :groupIdx)")
    List<FriendEntity> findAllFriendsNotInGroup(@Param("userIdx") int userIdx, @Param("groupIdx") int groupIdx);

    @Query("select f from FriendEntity f where f.user_idx1.userIdx = :userIdx and f.user_idx2.userIdx = :friendIdx")
    FriendEntity isFriendExist(@Param("userIdx") int userIdx, @Param("friendIdx") int friendIdx);

    @Query(nativeQuery = true, value = "select distinct substring_index(substring_index(substring_index(d.emo,'/',-1),'_',2),'_',-1) as emo, p.nickname from diary d join user_profile p on d.user_idx = p.user_idx where d.user_idx in (select f.user_idx2 from friend f where user_idx = :userIdx) and d.diary_date= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 DAY),'%Y-%m-%d')")
    List<Object[]> findEmotionOfLastDay(@Param("userIdx") int userIdx);
}

