package com.dbd.nanal.repository;

import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.model.GroupDiaryRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupDiaryRelationRepository extends JpaRepository<GroupDiaryRelationEntity, Integer> {

    List<GroupDiaryRelationEntity> findByDiary(DiaryEntity diary);
}
