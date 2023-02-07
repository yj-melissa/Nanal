package com.dbd.nanal.dto;

import com.dbd.nanal.model.PaintingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
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

    
    // 수정할 그룹 이미지 인덱스
    private int groupImgIdx;

    public PaintingEntity toEntity(){
        return PaintingEntity.builder().pictureTitle(pictureTitle).fileSize(fileSize).imgUrl(imgUrl).build();
    }

    public void init() {
        this.groupImgIdx = 0;
        this.imgUrl = "https://nanal-dbd.s3.ap-northeast-2.amazonaws.com/dalle/emo_sad.png";
//        this.imgUrl = "https://nanal-dbd.s3.ap-northeast-2.amazonaws.com/dalle/emo_joy.png";

    }
}
