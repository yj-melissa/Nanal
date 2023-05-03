package com.dbd.nanal.dto;


import com.dbd.nanal.model.PaintingEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaintingResponseDTO {
    private int pictureIdx;
    private String pictureTitle;
    private String imgUrl;

    public PaintingResponseDTO(PaintingEntity paintingEntity) {
        this.pictureIdx = paintingEntity.getPictureIdx();
        this.pictureTitle = paintingEntity.getPictureTitle();
        this.imgUrl = paintingEntity.getImgUrl();
    }

}
