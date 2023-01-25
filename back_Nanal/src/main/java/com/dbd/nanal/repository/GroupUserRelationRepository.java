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

    @Query("select a from GroupDetailEntity a join fetch GroupUserRelationEntity b on a.groupIdx=b.groupDetail.groupIdx where b.user.userIdx=:userIdx")
    List<GroupDetailEntity> findGroupList(@Param("userIdx") int userIdx);
}
