package com.dbd.nanal.repository;

import com.dbd.nanal.model.GroupTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupTagRepository extends JpaRepository<GroupTagEntity, Integer> {
//
//    @Query("select t from GroupTagEntity t where t.groupDetail.groupIdx = :groupIdx")
//    List<GroupTagEntity> findAllByGroupIdx(@Param("groupIdx") int groupIdx);

}
