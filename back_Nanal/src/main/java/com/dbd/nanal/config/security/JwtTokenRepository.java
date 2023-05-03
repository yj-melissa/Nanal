package com.dbd.nanal.config.security;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, String> {

    Optional<JwtTokenEntity> findByRefreshToken(String refreshToken);
    JwtTokenEntity findByUserIdx(int userIdx);
    JwtTokenEntity findByUserId(String userId);

    boolean existsByUserIdx(int userIdx);
    boolean existsByUserId(String userId);
}