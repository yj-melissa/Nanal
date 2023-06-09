package com.dbd.nanal.repository;

import com.dbd.nanal.model.GroupDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupDetailEntity, Integer> {

}
