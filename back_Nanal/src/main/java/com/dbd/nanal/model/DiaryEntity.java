package com.dbd.nanal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "diary")
@Getter
@Setter
public class DiaryEntity {

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
}
