package com.dbd.nanal.dto;

import com.dbd.nanal.model.DiaryEntity;
import lombok.Data;

import java.util.Date;

@Data
public class DiaryResponseDTO {
    private int diaryIdx;
    private int userIdx;
    private Date creationDate;
    private String content;
    private int picture;
    private int music;
    private boolean isDeleted;
    private Date deleteDate;
    private Date expireDate;
    private String emo;

    public DiaryResponseDTO(DiaryEntity diary) {
        this.diaryIdx = diary.getDiaryIdx();
        this.userIdx = diary.getUser().getUserIdx();
        this.creationDate = diary.getCreation_date();
        this.content = diary.getContent();
        this.picture = diary.getPainting().getPictureIdx();
        this.music = diary.getMusic().getMusicIdx();
        this.isDeleted = diary.isDeleted();
        this.deleteDate = diary.getDeleteDate();
        this.expireDate = diary.getExpireDate();
        this.emo = diary.getEmo();
    }
}
