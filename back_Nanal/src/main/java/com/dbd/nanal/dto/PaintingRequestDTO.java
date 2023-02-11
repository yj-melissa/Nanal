package com.dbd.nanal.dto;

import com.dbd.nanal.model.PaintingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class PaintingRequestDTO {

    private String pictureTitle;
    private String imgUrl;
    private MultipartFile multipartFile;
    private File file;
    private Date creationTime;
    private int groupIdx;
    private long fileSize;
    private int groupImgIdx;

    public PaintingRequestDTO(String pictureTitle, String imgUrl) {
        this.pictureTitle = pictureTitle;
        this.imgUrl = imgUrl;
    }

    public PaintingEntity toEntity() {
        return PaintingEntity.builder().pictureTitle(pictureTitle).imgUrl(imgUrl).build();
//        return PaintingEntity.builder().pictureTitle(pictureTitle).fileSize(fileSize).imgUrl(imgUrl).build();
    }

    public void init() {
        this.groupImgIdx = 0;
        this.setFileSize(256 * 256);
        Double random = Math.random() * 6; // 0.0~1.0

        System.out.println("random : " + random);

        if (random < 1) {
            this.imgUrl = Emotion.ANG.getImgUrl();
        } else if (random < 2) {
            this.imgUrl = Emotion.CALM.getImgUrl();
        } else if (random < 3) {
            this.imgUrl = Emotion.EMB.getImgUrl();
        } else if (random < 4) {
            this.imgUrl = Emotion.JOY.getImgUrl();
        } else if (random < 5) {
            this.imgUrl = Emotion.NERV.getImgUrl();
        } else {
            this.imgUrl = Emotion.SAD.getImgUrl();
        }

    }

}
