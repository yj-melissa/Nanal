package com.dbd.nanal.repository;

import com.dbd.nanal.model.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {
    @Query("select a from  DiaryEntity a join GroupDiaryRelationEntity b on  b.diary.diaryIdx=a.diaryIdx where b.groupDetail.groupIdx=:groupIdx and a.isDeleted=false")
    List<DiaryEntity> findGroupDiaryList(@Param("groupIdx") int groupIdx);

    @Query("select a from DiaryEntity a where a.user.userIdx=:userIdx and a.isDeleted=true")
    List<DiaryEntity> findByUserIdx(@Param("userIdx") int userIdx);

    @Query("select a from DiaryEntity a where function('DATE',a.creationDate)=:date")
    List<DiaryEntity> findDateDiaryList(@Param("date")Date date);

    @Query("select a from DiaryEntity a where function('YEAR', a.creationDate)=:year and function('MONTH',a.creationDate)=:month")
    List<DiaryEntity> findMonthDiaryList(@Param("year")int year, @Param("month")int month);
}
