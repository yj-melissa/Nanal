package com.dbd.nanal.model;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter @Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private int userIdx;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "login_type")
    private String loginType;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_access_date")
    private LocalDateTime lastAccessDate;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "user_password")
    private String password;

    @Column(name = "social_code")
    private int socialCode;


    
    // 연결관계

    @OneToOne(mappedBy = "user", fetch = LAZY)
    private UserProfileEntity userProfile;

    @OneToOne
    @JoinColumn(name = "emotion_idx")
    private EmotionEntity emotion;

    @OneToMany(mappedBy = "user")
    private List<GroupUserRelationEntity> groupUserRelations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<NoticeEntity> notices = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ScrapEntity> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DiaryCommentEntity> diaryComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DiaryEntity> diaries = new ArrayList<>();




}
