package com.dbd.nanal.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private UserEntity user;

    @CreationTimestamp
    private Timestamp creation_date;

    private String content;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="pictureIdx")
    private PaintingEntity painting;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="music_idx")
    private MusicEntity music;

    @Column(name="is_deleted")
    private boolean isDeleted;

    @Column(name="delete_date")
    private Timestamp deleteDate;

    @Column(name="expire_date")
    private Timestamp expireDate;

    private String emo;

    @OneToMany(mappedBy = "diary")
    private List<KeywordEntity> keywords=new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<DiaryCommentEntity> diariesComments=new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<ScrapEntity> scraps=new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<GroupDiaryRelationEntity> groupDiaryRelations=new ArrayList<>();

    @Builder
    public DiaryEntity(int diaryIdx, UserEntity user, Timestamp creation_date,
                       String content, PaintingEntity painting, MusicEntity music, boolean isDeleted,
                       Timestamp deleteDate, Timestamp expireDate, String emo) {
        this.diaryIdx=diaryIdx;
        this.user=user;
        this.creation_date=creation_date;
        this.content=content;
        this.painting=painting;
        this.music=music;
        this.isDeleted=isDeleted;
        this.deleteDate=deleteDate;
        this.expireDate=expireDate;
        this.emo=emo;
    }
}
