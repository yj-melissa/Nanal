package com.dbd.nanal.repository;

import com.dbd.nanal.dto.GroupListResponseDTO;
import com.dbd.nanal.model.FriendEntity;
import com.dbd.nanal.model.GroupDetailEntity;
import com.dbd.nanal.model.GroupUserRelationEntity;
import com.dbd.nanal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public interface GroupUserRelationRepository extends JpaRepository<GroupUserRelationEntity, Integer> {

    // 일기 최신 작성 순으로 불러오기
    // select distinct g.group_idx, g.group_name, g.creation_date, d.picture_idx, d.creation_date `일기 날짜` from group_detail g  left join group_user_relation r on g.group_idx = r.group_idx left join diary d on r.user_idx =  d.user_idx  where r.user_idx= 2 order by d.creation_date desc;
//    @Query("select distinct new com.dbd.nanal.dto.GroupListResponseDTO(g.groupIdx, g.groupName, g.imgUrl, d.creationDate) from GroupDetailEntity g inner join GroupUserRelationEntity r on g.groupIdx=r.groupDetail.groupIdx inner join DiaryEntity d on r.user.userIdx=d.user.userIdx where r.user.userIdx = :userIdx")
//    @Query(nativeQuery = true, value = "select distinct g.group_idx, g.group_name, g.img_url,  d.creation_date from group_detail g  left join group_user_relation r on g.group_idx = r.group_idx left join diary d on r.user_idx =  d.user_idx  where r.user_idx= :userIdx order by d.creation_date desc")
//    @Query("select distinct new com.dbd.nanal.dto.GroupListResponseDTO(g.groupIdx, g.groupName, g.imgUrl, d.creationDate) from GroupDetailEntity g inner join GroupUserRelationEntity r on g.groupIdx=r.groupDetail.groupIdx inner join DiaryEntity d on r.user.userIdx=d.user.userIdx where r.user.userIdx = :userIdx")
//    @Query("select distinct g  from GroupDetailEntity g join GroupUserRelationEntity r on g.groupIdx=r.groupDetail.groupIdx join DiaryEntity d on r.user.userIdx=d.user.userIdx where r.user.userIdx = :userIdx order by  d.creationDate desc")
    @Query("select d from GroupDetailEntity d join fetch GroupUserRelationEntity r on d.groupIdx=r.groupDetail.groupIdx where r.user.userIdx=:userIdx")
//    List<GroupListResponseDTO> findGroupList(@Param("userIdx") int userIdx);
//    List<Map<String, Object>> findGroupList(@Param("userIdx") int userIdx);
    List<GroupDetailEntity> findGroupList(@Param("userIdx") int userIdx);

    @Query("select r from GroupUserRelationEntity r where r.user.userIdx = :userIdx and r.groupDetail.groupIdx = :groupIdx")
    GroupUserRelationEntity findByUserIdGroupID(@Param("userIdx") int userIdx, @Param("groupIdx") int groupIdx);

    List<GroupUserRelationEntity> findByGroupDetail(GroupDetailEntity groupDetailEntity);

    @Query("select u from UserEntity u join fetch GroupUserRelationEntity r on u.userIdx = r.user.userIdx where r.user.userIdx != :userIdx and r.groupDetail.groupIdx = :groupIdx")
    List<UserEntity> findGroupUser(@Param("userIdx") int userIdx, @Param("groupIdx") int groupIdx);
}
