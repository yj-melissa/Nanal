package com.dbd.nanal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "group_detail")
@Getter
@Setter
@NoArgsConstructor // 엔티티는 기본 생성자 필수
public class GroupDetailEntity {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private int groupIdx;

    @Column(name = "group_name", length = 30)
    private String groupName;

    @Column(name = "is_private")
    private boolean isPrivate;

    private int groupImgIdx;

    private String imgUrl;

    @CreationTimestamp
    private Date creationDate;


    @OneToMany(mappedBy = "groupDetail", cascade = CascadeType.REMOVE) // 읽기만 가능
    @JsonIgnore
    private List<GroupTagEntity> groupTags = new ArrayList<>();

    @OneToMany(mappedBy = "groupDetail", cascade = CascadeType.REMOVE) // 읽기만 가능
    @JsonIgnore
    private List<GroupUserRelationEntity> groupUserRelations = new ArrayList<>();

    @OneToMany(mappedBy = "groupDetail", cascade = CascadeType.REMOVE) // 읽기만 가능
    @JsonIgnore
    private List<GroupDiaryRelationEntity> groupDiaries = new ArrayList<>();

    @OneToMany(mappedBy = "groupDetail", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<DiaryCommentEntity> diaryComments = new ArrayList<>();


    @Builder
    public GroupDetailEntity(int groupIdx, String groupName, boolean isPrivate, int groupImgIdx, Date creationDate, List<GroupTagEntity> groupTags, List<GroupUserRelationEntity> groupUserRelations, List<GroupDiaryRelationEntity> groupDiaries, List<DiaryCommentEntity> diaryComments, String imgUrl) {
        this.groupIdx = groupIdx;
        this.groupName = groupName;
        this.isPrivate = isPrivate;
        this.groupImgIdx = groupImgIdx;
        this.creationDate = creationDate;
        this.groupTags = groupTags;
        this.groupUserRelations = groupUserRelations;
        this.groupDiaries = groupDiaries;
        this.diaryComments = diaryComments;
        this.imgUrl = imgUrl;
    }

}
