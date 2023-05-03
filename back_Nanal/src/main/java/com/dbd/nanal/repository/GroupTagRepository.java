package com.dbd.nanal.repository;

import com.dbd.nanal.model.GroupTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupTagRepository extends JpaRepository<GroupTagEntity, Integer> {

}
