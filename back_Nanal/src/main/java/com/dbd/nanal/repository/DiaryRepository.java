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
    @Query("select a from  DiaryEntity a join GroupDiaryRelationEntity b on  b.diary.diaryIdx=a.diaryIdx where b.groupDetail.groupIdx=:groupIdx and a.isDeleted=false order by  a.diaryDate desc, a.diaryIdx desc ")
    List<DiaryEntity> findGroupDiaryList(@Param("groupIdx") int groupIdx);

    @Query("select a from DiaryEntity a where a.user.userIdx=:userIdx and a.isDeleted=true order by a.diaryDate desc , a.diaryIdx desc ")
    List<DiaryEntity> findByUserIdx(@Param("userIdx") int userIdx);


    // 최신 일기 가져오기
    @Query(nativeQuery = true, value = "select * from diary a where a.user_idx = :userIdx order by a.creation_date desc LIMIT 1")
    DiaryEntity findLatestDiary(@Param("userIdx") int userIdx);

    @Query("select a from DiaryEntity a where a.diaryDate=:date and a.isDeleted=false and a.user.userIdx=:userIdx order by a.diaryIdx desc ")
    List<DiaryEntity> findDateDiaryList(@Param("date") Date date, @Param("userIdx") int userIdx);
//    @Query("select a from DiaryEntity a where function('YEAR', a.creationDate)=:year and function('MONTH',a.creationDate)=:month and function('DAYOFMONTH', a.creationDate)=:date and a.isDeleted=false and a.user.userIdx=:userIdx")
//    List<DiaryEntity> findDateDiaryList(@Param("year")int year, @Param("month")int month, @Param("date") int date, @Param("userIdx") int userIdx);

    @Query("select a from DiaryEntity a where function('YEAR', a.diaryDate)=:year and function('MONTH',a.diaryDate)=:month and a.isDeleted=false and a.user.userIdx=:userIdx order by a.diaryDate desc, a.diaryIdx desc")
    List<DiaryEntity> findMonthDiaryList(@Param("year") int year, @Param("month") int month, @Param("userIdx") int userIdx);

    @Query("select a from DiaryEntity a where a.user.userIdx=:userIdx and a.isDeleted=false order by a.diaryDate desc , a.diaryIdx desc ")
    List<DiaryEntity> findByUserIdxList(@Param("userIdx") int userIdx);

    @Query("select count(a) from DiaryEntity a where a.user.userIdx=:userIdx and a.isDeleted=false")
    int countUserDiary(@Param("userIdx") int userIdx);

    @Query("select a from DiaryEntity a where a.content like concat('%', :content, '%') and a.diaryIdx in (select g.diary.diaryIdx from GroupDiaryRelationEntity g where g.groupDetail.groupIdx IN (select u.groupDetail.groupIdx from  GroupUserRelationEntity u where u.user.userIdx=:userIdx) )")
    List<DiaryEntity> searchDiary(@Param("content") String content, @Param("userIdx") int userIdx);

}
