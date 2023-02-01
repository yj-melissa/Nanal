package com.dbd.nanal.repository;

import com.dbd.nanal.model.GroupDetailEntity;
import com.dbd.nanal.model.GroupUserRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupUserRelationRepository extends JpaRepository<GroupUserRelationEntity, Integer> {

    @Query("select d from GroupDetailEntity d join fetch GroupUserRelationEntity r on d.groupIdx=r.groupDetail.groupIdx where r.user.userIdx=:userIdx")
    List<GroupDetailEntity> findGroupList(@Param("userIdx") int userIdx);

    @Query("select r from GroupUserRelationEntity r where r.user.userIdx = :userIdx and r.groupDetail.groupIdx = :groupIdx")
    GroupUserRelationEntity findByUserIdGroupID(@Param("userIdx") int userIdx, @Param("groupIdx") int groupIdx);

    List<GroupUserRelationEntity> findByGroupDetail(GroupDetailEntity groupDetailEntity);
}
