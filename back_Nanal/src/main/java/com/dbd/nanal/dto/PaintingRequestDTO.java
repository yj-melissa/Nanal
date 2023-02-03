package com.dbd.nanal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class PaintingRequestDTO {

    private String pictureTitle;
    private String picturePath;
    private MultipartFile file;
    private LocalDateTime creationTime;

}
