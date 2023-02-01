package com.dbd.nanal.repository;

import com.dbd.nanal.model.JwtTokenEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, String> {

    Optional<JwtTokenEntity> findByRefreshToken(String refreshToken);
    JwtTokenEntity findByUserIdx(int userIdx);
    boolean existsByUserIdx(int userIdx);
}