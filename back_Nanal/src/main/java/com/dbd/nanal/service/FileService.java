package com.dbd.nanal.service;


import com.dbd.nanal.dto.PaintingRequestDTO;
import com.dbd.nanal.handler.FileHandler;
import com.dbd.nanal.model.PaintingEntity;
import com.dbd.nanal.repository.PaintingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class FileService {

    private final PaintingRepository paintingRepository;

    public FileService(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

    @Transactional
    public PaintingEntity paintingSave(PaintingRequestDTO paintingRequestDTO) throws IOException {
        FileHandler handler = new FileHandler();
        PaintingEntity paintingEntity = handler.parseFileInfo(paintingRequestDTO);
        System.out.println("그림 이름 : " + paintingEntity.getPictureTitle());
        System.out.println("그림 사이즈 : " + paintingEntity.getFileSize());
        System.out.println("시간 : " + paintingEntity.getCreationDate());

        return paintingRepository.save(paintingEntity);
    }
}
