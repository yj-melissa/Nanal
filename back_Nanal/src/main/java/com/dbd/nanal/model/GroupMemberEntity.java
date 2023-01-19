package com.dbd.nanal.model;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_member")
@Getter
public class GroupMemberEntity {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment
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
    private Timestamp creationDate;


    // 태그 검색할 때 사용
    @OneToMany(mappedBy = "groupMember") // 읽기만 가능
    private List<GroupTagEntity> groupTags = new ArrayList<>();

    @OneToMany(mappedBy = "groupMember") // 읽기만 가능
    private List<GroupUserRelationEntity> groupUserRelations = new ArrayList<>();

    @OneToMany(mappedBy = "groupMember") // 읽기만 가능
    private List<GroupDiaryRelationEntity> groupDiaries = new ArrayList<>();

    @OneToMany(mappedBy = "groupMember")
    private List<DiaryCommentEntity> diaryComments = new ArrayList<>();

    @Builder
    public GroupMemberEntity(int groupIdx, String groupName, boolean isPrivate, String groupImg, Timestamp creationDate) {
        this.groupIdx = groupIdx;
        this.groupName = groupName;
        this.isPrivate = isPrivate;
        this.groupImg = groupImg;
        this.creationDate = creationDate;
    }

    public GroupMemberEntity() {

    }

//    @PrePersist
//    public void prePersist() {
//        this.isPrivate = false;
//    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

}
