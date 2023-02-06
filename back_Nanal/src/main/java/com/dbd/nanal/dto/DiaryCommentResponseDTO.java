package com.dbd.nanal.dto;

import com.dbd.nanal.model.DiaryCommentEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class DiaryCommentResponseDTO {
    private int commentIdx;
    private String content;
    private LocalDateTime creationDate;
    private int userIdx;
    private String nickname;

    public DiaryCommentResponseDTO(DiaryCommentEntity diaryCommentEntity) {
        this.commentIdx=diaryCommentEntity.getCommentIdx();
        this.content=diaryCommentEntity.getContent();
        this.creationDate=diaryCommentEntity.getCreationDate();
        this.userIdx=diaryCommentEntity.getUser().getUserIdx();
        this.nickname=diaryCommentEntity.getUser().getUserProfile().getNickname();
    }
}
