package com.dbd.nanal.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "painting")
public class PaintingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="picture_idx")
    private int pictureIdx;

    @Column(name="picture_path")
    private String picturePath;

    @CreationTimestamp
    @Column(name="creation_date")
    private Timestamp creationDate;

    private String picture;

    @OneToOne(mappedBy = "painting")
    private DiaryEntity diary;

    @Builder
    public PaintingEntity(int pictureIdx, String picturePath, Timestamp creationDate, String picture) {
        this.pictureIdx=pictureIdx;
        this.picturePath=picturePath;
        this.creationDate=creationDate;
        this.picture=picture;
    }
}
