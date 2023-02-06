package com.dbd.nanal.dto;

import com.dbd.nanal.model.NoticeEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class NotificationResponseDTO {
    private int noticeIdx;
    private int userIdx;
    private int requestUserIdx;
    private int requestGroupIdx;
    private int requestDiaryIdx;
    private int noticeType;
    private String content;
    private boolean isChecked;
    private LocalDateTime creationDate;

    public NotificationResponseDTO(NoticeEntity noticeEntity) {
        this.noticeIdx = noticeEntity.getNoticeIdx();
        this.userIdx = noticeEntity.getUser().getUserIdx();
        this.requestUserIdx = noticeEntity.getRequestUserIdx();
        this.requestGroupIdx = noticeEntity.getRequestDiaryIdx();
        this.requestDiaryIdx = noticeEntity.getRequestDiaryIdx();
        this.noticeType = noticeEntity.getNoticeType();
        this.content = noticeEntity.getContent();
        this.isChecked = noticeEntity.getIsChecked();
        this.creationDate = noticeEntity.getCreationDate();
    }
}
