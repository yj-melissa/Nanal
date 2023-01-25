package com.dbd.nanal.repository;

import com.dbd.nanal.model.DiaryCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryCommentRepository extends JpaRepository<DiaryCommentEntity, Integer> {
}
