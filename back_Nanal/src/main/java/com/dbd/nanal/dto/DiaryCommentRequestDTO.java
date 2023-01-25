package com.dbd.nanal.dto;

import com.dbd.nanal.model.DiaryCommentEntity;
import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.model.GroupDetailEntity;
import com.dbd.nanal.model.UserEntity;
import lombok.Getter;

@Getter
public class DiaryCommentRequestDTO {
    private int diaryIdx;
    private int userIdx;
    private int groupIdx;
    private String content;

    public DiaryCommentEntity toEntity(DiaryEntity diary, UserEntity user, GroupDetailEntity group){
        return DiaryCommentEntity.builder()
                .diary(diary)
                .user(user)
                .groupDetail(group)
                .content(content)
                .build();
    }
}
