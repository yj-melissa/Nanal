package com.dbd.nanal.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "diary")
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diary_idx")
    private int diaryIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private UserEntity user;

    @Column(name="creation_date")
    @CreationTimestamp
    private Date creationDate;

    private String content;

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

    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<KeywordEntity> keywords=new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<DiaryCommentEntity> diariesComments=new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<ScrapEntity> scraps=new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.REMOVE)
    private List<GroupDiaryRelationEntity> groupDiaryRelations=new ArrayList<>();

    @Builder
    public DiaryEntity(int diaryIdx, UserEntity user, Date creationDate,
                       String content, PaintingEntity painting, MusicEntity music, boolean isDeleted,
                       Date deleteDate, Date expireDate, String emo) {
        this.diaryIdx=diaryIdx;
        this.user=user;
        this.creationDate=creationDate;
        this.content=content;
        this.painting=painting;
        this.music=music;
        this.isDeleted=isDeleted;
        this.deleteDate=deleteDate;
        this.expireDate=expireDate;
        this.emo=emo;
    }

    public void deleteDiary(Boolean isDeleted, Date deleteDate, Date expireDate){
        this.isDeleted=isDeleted;
        this.deleteDate=deleteDate;
        this.expireDate=expireDate;
    }
}