package com.dbd.nanal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="diary_comment")
public class DiaryCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_idx", columnDefinition = "INT UNSIGNED")
    private int commentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_idx")
    @JsonIgnore
    private DiaryEntity diary;

    private String content;

    @CreationTimestamp
    @Column(name="creation_date")
    private LocalDateTime creationDate;

    @Column(name="parent_comment_idx")
    private int parentCommentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    @JsonIgnore
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_idx")
    @JsonIgnore
    private GroupDetailEntity groupDetail;

    @Builder
    public DiaryCommentEntity(int commentIdx, DiaryEntity diary, String content, LocalDateTime creationDate,
                              int parentCommentIdx, UserEntity user, GroupDetailEntity groupDetail) {
        this.commentIdx=commentIdx;
        this.diary=diary;
        this.content=content;
        this.creationDate=creationDate;
        this.parentCommentIdx=parentCommentIdx;
        this.user=user;
        this.groupDetail=groupDetail;
    }

}
