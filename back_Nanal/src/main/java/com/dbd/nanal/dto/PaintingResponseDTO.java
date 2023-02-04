package com.dbd.nanal.dto;


import com.dbd.nanal.model.PaintingEntity;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
public class PaintingResponseDTO {
    private int pictureIdx;
    private String pictureTitle;

    public PaintingResponseDTO(PaintingEntity paintingEntity) {
        this.pictureIdx = paintingEntity.getPictureIdx();
        this.pictureTitle = paintingEntity.getPictureTitle();
    }
}
