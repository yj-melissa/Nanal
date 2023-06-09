package com.dbd.nanal.dto;

import com.dbd.nanal.model.DiaryCommentEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryCommentResponseDTO {
    private int commentIdx;
    private String content;
    private LocalDateTime creationDate;
    private int userIdx;
    private String nickname;
    private String img;

    public DiaryCommentResponseDTO(DiaryCommentEntity diaryCommentEntity) {
        this.commentIdx=diaryCommentEntity.getCommentIdx();
        this.content=diaryCommentEntity.getContent();
        this.creationDate=diaryCommentEntity.getCreationDate();
        this.userIdx=diaryCommentEntity.getUser().getUserIdx();
        this.nickname=diaryCommentEntity.getUser().getUserProfile().getNickname();
        this.img=diaryCommentEntity.getUser().getUserProfile().getImg();
    }
}
