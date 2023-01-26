package com.dbd.nanal.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name="diary_comment")
public class DiaryCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_idx")
    private int commentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_idx")
    private DiaryEntity diary;

    private String content;

    @CreationTimestamp
    @Column(name="creation_date")
    private Timestamp creationDate;

    @Column(name="parent_comment_idx")
    private int parentCommentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_idx")
    private GroupDetailEntity groupDetail;

    @Builder
    public DiaryCommentEntity(int commentIdx, DiaryEntity diary, String content, Timestamp creationDate,
                              int parentCommentIdx, UserEntity user, GroupDetailEntity groupDetail) {
        this.commentIdx=commentIdx;
        this.diary=diary;
        this.content=content;
        this.creationDate=creationDate;
        this.parentCommentIdx=parentCommentIdx;
        this.user=user;
        this.groupDetail=groupDetail;
    }

    public void updateComment(String content){
        this.content=content;
    }
}
