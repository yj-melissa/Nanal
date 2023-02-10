package com.dbd.nanal.repository;

import com.dbd.nanal.model.PaintingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaintingRepository extends JpaRepository<PaintingEntity, Integer> {

}
