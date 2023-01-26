package com.dbd.nanal.service;

import com.dbd.nanal.config.security.JwtTokenProvider;
import com.dbd.nanal.dto.JwtTokenDTO;
import com.dbd.nanal.model.JwtTokenEntity;
import com.dbd.nanal.repository.JwtTokenRepository;
import io.jsonwebtoken.JwtException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenRepository jwtTokenRepository;

    @Transactional
    public void login(JwtTokenDTO jwtTokenDTO) {

        String userId = jwtTokenDTO.getUserId();

        JwtTokenEntity jwtToken = JwtTokenEntity.builder()
            .userId(userId)
            .refreshToken(jwtTokenDTO.getRefreshToken())
            .build();

        jwtTokenRepository.deleteByuserId(userId);
        if(jwtTokenRepository.existsByUserId(userId)) {
            // 기존 Refresh 토큰 삭제
            jwtTokenRepository.deleteByuserId(userId);
        }
        jwtTokenRepository.save(jwtToken);

    }

    public Optional<JwtTokenEntity> getRefreshToken(String refreshToken){

        return jwtTokenRepository.findByRefreshToken(refreshToken);
    }



}
