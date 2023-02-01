package com.dbd.nanal.repository;

import com.dbd.nanal.model.ScrapEntity;
import com.dbd.nanal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<ScrapEntity, Integer> {

    List<ScrapEntity> findByUser(UserEntity user);
}
