package com.dbd.nanal.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Setter
@Table(name = "painting")
public class PaintingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private int pictureIdx;
    private String picturePath;
    @CreationTimestamp
    private Date creationDate;
    private String pictureTitle;
    private long fileSize;

//    @OneToOne(mappedBy = "painting")
//    @JsonIgnore
//    private DiaryEntity diary;

    @Builder
    public PaintingEntity(int pictureIdx, String picturePath, String pictureTitle, long fileSize, Date creationDate) {
        this.pictureIdx=pictureIdx;
        this.picturePath=picturePath;
        this.pictureTitle=pictureTitle;
        this.fileSize = fileSize;
        this.creationDate = creationDate;
    }
}
