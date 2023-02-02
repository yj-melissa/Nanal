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
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment
    @Column(columnDefinition = "INT UNSIGNED")
    private int groupIdx;

    @Column(name = "group_name", length = 30)
    private String groupName;

    // 확장 - 불특정 다수에게 설정
    @Column(name = "is_private")
    private boolean isPrivate;

    @Column(name = "group_img")
    private String groupImg;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Date creationDate;


    // 태그 검색할 때 사용
    @OneToMany(mappedBy = "groupDetail") // 읽기만 가능
    @JsonIgnore
    private List<GroupTagEntity> groupTags = new ArrayList<>();

    @OneToMany(mappedBy = "groupDetail") // 읽기만 가능
    @JsonIgnore
    private List<GroupUserRelationEntity> groupUserRelations = new ArrayList<>();

    @OneToMany(mappedBy = "groupDetail") // 읽기만 가능
    @JsonIgnore
    private List<GroupDiaryRelationEntity> groupDiaries = new ArrayList<>();

    @OneToMany(mappedBy = "groupDetail")
    @JsonIgnore
    private List<DiaryCommentEntity> diaryComments = new ArrayList<>();

//    @Builder
//    public GroupMemberEntity(int groupIdx, String groupName, boolean isPrivate, String groupImg, Date creationDate) {
//        this.groupIdx = groupIdx;
//        this.groupName = groupName;
//        this.isPrivate = isPrivate;
//        this.groupImg = groupImg;
//        this.creationDate = creationDate;
//    }

    @Builder
    public GroupDetailEntity(int groupIdx, String groupName, boolean isPrivate, String groupImg, Date creationDate, List<GroupTagEntity> groupTags, List<GroupUserRelationEntity> groupUserRelations, List<GroupDiaryRelationEntity> groupDiaries, List<DiaryCommentEntity> diaryComments) {
        this.groupIdx = groupIdx;
        this.groupName = groupName;
        this.isPrivate = isPrivate;
        this.groupImg = groupImg;
        this.creationDate = creationDate;
        this.groupTags = groupTags;
        this.groupUserRelations = groupUserRelations;
        this.groupDiaries = groupDiaries;
        this.diaryComments = diaryComments;
    }


    public boolean getIsPrivate() {
        return isPrivate;
    }

}
