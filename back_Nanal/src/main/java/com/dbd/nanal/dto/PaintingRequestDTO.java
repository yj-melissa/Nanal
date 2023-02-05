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

    public PaintingEntity toEntity(){
        return PaintingEntity.builder().pictureTitle(pictureTitle).fileSize(fileSize).imgUrl(imgUrl).build();
    }

}
