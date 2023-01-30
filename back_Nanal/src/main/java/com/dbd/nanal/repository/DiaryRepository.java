package com.dbd.nanal.repository;

import com.dbd.nanal.model.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {
    @Query("select a from  DiaryEntity a join GroupDiaryRelationEntity b on  b.diary.diaryIdx=a.diaryIdx where b.groupDetail.groupIdx=:groupIdx and a.isDeleted=false")
    List<DiaryEntity> findGroupDiaryList(@Param("groupIdx") int groupIdx);

    @Query("select a from DiaryEntity a where a.user.userIdx=:userIdx and a.isDeleted=true")
    List<DiaryEntity> findByUserIdx(@Param("userIdx") int userIdx);


    // 최신 일기 가져오기
    @Query(nativeQuery = true, value = "select * from Diary a where a.user_idx = :userIdx order by a.creation_date desc LIMIT 1")
    DiaryEntity findLatestDiary(@Param("userIdx") int userIdx);
}
