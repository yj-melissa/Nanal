package com.dbd.nanal.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Setter
@Table(name = "painting")
public class PaintingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="picture_idx", columnDefinition = "INT UNSIGNED")
    private int pictureIdx;

//    @Column(name="picture_path")
    private String picturePath;

    @Column(name = "creation_date", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @CreatedDate
    private LocalDateTime creationDate;

    private String pictureTitle;

    private long fileSize;

//    @OneToOne(mappedBy = "painting")
//    @JsonIgnore
//    private DiaryEntity diary;

    @Builder
    public PaintingEntity(int pictureIdx, String picturePath, String pictureTitle, long fileSize, LocalDateTime creationDate) {
        this.pictureIdx=pictureIdx;
        this.picturePath=picturePath;
        this.pictureTitle=pictureTitle;
        this.fileSize = fileSize;
        this.creationDate = creationDate;
    }
}
