package com.dbd.nanal.repository;

import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.model.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Integer> {

    List<KeywordEntity> findByDiary(DiaryEntity diary);
}
