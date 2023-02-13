package com.dbd.nanal.repository;

import com.dbd.nanal.model.GroupDetailEntity;
import com.dbd.nanal.model.GroupUserRelationEntity;
import com.dbd.nanal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupUserRelationRepository extends JpaRepository<GroupUserRelationEntity, Integer> {

    @Query("select d from GroupDetailEntity d join fetch GroupUserRelationEntity r on d.groupIdx=r.groupDetail.groupIdx where r.user.userIdx = :userIdx order by d.groupName")
    List<GroupDetailEntity> findGroupListByName(@Param("userIdx") int userIdx);

    @Query(nativeQuery = true, value = "select de.group_idx, de.img_url, de.group_name, t.tag_idx, t.tag from group_detail de join group_tag t on de.group_idx = t.group_idx left join\n" +
            "            (select d.diary_idx, d.creation_date, gd.group_idx from diary d join group_diary gd on d.diary_idx = gd.diary_idx\n" +
            "             group by gd.group_idx ) b on de.group_idx = b.group_idx where de.group_idx in (select r.group_idx from group_user_relation r where r.user_idx = :userIdx)" +
            "            order by b.creation_date desc, de.group_name, t.tag_idx")
    List<Object[]> findGroupListByTime(@Param("userIdx") int userIdx);

    @Query("select r from GroupUserRelationEntity r where r.user.userIdx = :userIdx and r.groupDetail.groupIdx = :groupIdx")
    GroupUserRelationEntity findByUserIdGroupID(@Param("userIdx") int userIdx, @Param("groupIdx") int groupIdx);

    List<GroupUserRelationEntity> findByGroupDetail(GroupDetailEntity groupDetailEntity);

    @Query("select u from UserEntity u join fetch GroupUserRelationEntity r on u.userIdx = r.user.userIdx where r.user.userIdx != :userIdx and r.groupDetail.groupIdx = :groupIdx")
    List<UserEntity> findGroupUser(@Param("userIdx") int userIdx, @Param("groupIdx") int groupIdx);

}

