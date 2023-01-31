package com.dbd.nanal.repository;

import com.dbd.nanal.model.NoticeEntity;
import com.dbd.nanal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {

    List<NoticeEntity> findByUser(UserEntity user);
}
