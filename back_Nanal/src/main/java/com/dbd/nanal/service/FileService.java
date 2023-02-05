package com.dbd.nanal.service;


import com.dbd.nanal.S3Uploader.S3Uploader;
import com.dbd.nanal.dto.PaintingRequestDTO;
import com.dbd.nanal.dto.PaintingResponseDTO;
import com.dbd.nanal.repository.PaintingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    private final PaintingRepository paintingRepository;
    private final S3Uploader s3Uploader;

    public FileService(PaintingRepository paintingRepository, S3Uploader s3Uploader) {
        this.paintingRepository = paintingRepository;
        this.s3Uploader = s3Uploader;

    }

    @Transactional
    public PaintingResponseDTO paintingSave(PaintingRequestDTO paintingRequestDTO)  {

        return new PaintingResponseDTO(paintingRepository.save(paintingRequestDTO.toEntity()));
    }

    public String saveToS3(File file) throws IOException {
        return s3Uploader.upload(file, "dalle");
    }
}
