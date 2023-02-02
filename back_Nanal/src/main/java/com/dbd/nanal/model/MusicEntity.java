package com.dbd.nanal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "music")
public class MusicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="music_idx", columnDefinition = "INT UNSIGNED")
    private int musicIdx;

    private String title;
    private String artist;

    @Column(name="music_path")
    private String musicPath;

    @CreationTimestamp
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name="music_title")
    private String musicTitle;

    @OneToOne(mappedBy = "music")
    @JsonIgnore
    private DiaryEntity diary;

    @Builder
    public MusicEntity(int musicIdx, String title, String artist, String musicPath, Timestamp creationDate, String musicTitle) {
        this.musicIdx=musicIdx;
        this.title=title;
        this.artist=artist;
        this.musicPath=musicPath;
        this.creationDate=creationDate;
        this.musicTitle=musicTitle;
    }
}
