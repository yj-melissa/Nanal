package com.dbd.nanal.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    private String imgUrl;
    @CreationTimestamp
    private Date creationDate;
    private String pictureTitle;
    private long fileSize;

    @Builder
    public PaintingEntity(int pictureIdx, String imgUrl, String pictureTitle, long fileSize, Date creationDate) {
        this.pictureIdx = pictureIdx;
        this.imgUrl = imgUrl;
        this.pictureTitle = pictureTitle;
        this.fileSize = fileSize;
        this.creationDate = creationDate;
    }

}
