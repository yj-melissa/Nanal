package com.dbd.nanal.service;

import com.dbd.nanal.model.PaintingEntity;
import com.dbd.nanal.repository.PaintingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaintingService {

    private final PaintingRepository paintingRepository;

    public PaintingService(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

    public PaintingEntity save(PaintingEntity paintingEntity) {
        return paintingRepository.save(paintingEntity);
    }

}
