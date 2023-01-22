package com.dbd.nanal.repository;


import com.dbd.nanal.model.GroupDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// 엔티티와 기본키의 자료형을 Generic으로 넣어준다.
public interface GroupRepository extends JpaRepository<GroupDetailEntity, Integer> {

}
