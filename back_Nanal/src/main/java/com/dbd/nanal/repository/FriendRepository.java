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

    @Query("select f from FriendEntity f where f.user_idx2.userIdx = :userIdx and f.user_idx1.userIdx not in (select g.user.userIdx from GroupUserRelationEntity g where g.groupDetail.groupIdx = :groupIdx)")
    List<FriendEntity> findAllFriendsNotInGroup(@Param("userIdx") int userIdx, @Param("groupIdx") int groupIdx);

    @Query("select f from FriendEntity f where f.user_idx1.userIdx = :userIdx and f.user_idx2.userIdx = :friendIdx")
    FriendEntity isFriendExist(@Param("userIdx") int userIdx, @Param("friendIdx") int friendIdx);
//    @Query("select f from FriendEntity f where f.user_idx2.userIdx = :userIdx and f.user_idx1.userIdx not in (select g.user.userIdx from GroupUserRelationEntity g where g.groupDetail.groupIdx = :groupIdx)")
//    List<FriendEntity> findAllFriendsNotInGroup(@Param("userIdx") int userIdx, @Param("groupIdx") int groupIdx);
}

//  access to the resource is denied.
//See 'docker run --help'.
// select * from user where user_id like '%test%' and user.user_idx Not In (select f.user_idx from friend f where f.user_idx2=3) and user.user_idx!=3;