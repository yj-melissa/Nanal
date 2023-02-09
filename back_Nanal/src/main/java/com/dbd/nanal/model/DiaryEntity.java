package com.dbd.nanal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "diary")
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diary_idx", columnDefinition = "INT UNSIGNED")
    private int diaryIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    @JsonIgnore
    private UserEntity user;

    @Column(name="creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name="diary_date")
    private Date diaryDate;

    private String content;

    private String imgUrl;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name="pictureIdx")
    private PaintingEntity painting;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name="music_idx")
    private MusicEntity music;

    @Column(name="is_deleted")
    private boolean isDeleted;

    @Column(name="delete_date")
    private Date deleteDate;

    @Column(name="expire_date")
    private Date expireDate;

    private String emo;

    @JsonIgnore
    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<KeywordEntity> keywords=new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<DiaryCommentEntity> diariesComments=new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<ScrapEntity> scraps=new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<GroupDiaryRelationEntity> groupDiaryRelations=new ArrayList<>();

    @Builder
    public DiaryEntity(int diaryIdx, UserEntity user, LocalDateTime creationDate, Date diaryDate,
                       String content, PaintingEntity painting, MusicEntity music, boolean isDeleted,
                       Date deleteDate, Date expireDate, String emo, String imgUrl) {
        this.diaryIdx=diaryIdx;
        this.user=user;
        this.creationDate=creationDate;
        this.diaryDate=diaryDate;
        this.content=content;
        this.painting=painting;
        this.music=music;
        this.isDeleted=isDeleted;
        this.deleteDate=deleteDate;
        this.expireDate=expireDate;
        this.emo=emo;
        this.imgUrl=imgUrl;
    }

}