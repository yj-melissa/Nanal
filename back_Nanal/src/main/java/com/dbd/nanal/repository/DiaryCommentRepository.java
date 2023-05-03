package com.dbd.nanal.repository;

import com.dbd.nanal.model.DiaryCommentEntity;
import com.dbd.nanal.model.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryCommentRepository extends JpaRepository<DiaryCommentEntity, Integer> {
    @Query("select a from  DiaryCommentEntity a where a.diary.diaryIdx=:diaryIdx and a.diary.isDeleted=false and a.groupDetail.groupIdx=:groupIdx")
    List<DiaryCommentEntity> findGroupDiaryCommentList(@Param("groupIdx") int groupIdx, @Param("diaryIdx") int diaryIdx);

    List<DiaryCommentEntity> findByDiary(DiaryEntity diary);
}
