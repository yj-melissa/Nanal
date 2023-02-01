package com.dbd.nanal.dto;

import com.dbd.nanal.model.DiaryEntity;
import lombok.Getter;

import java.util.Date;

@Getter
public class DiaryResponseDTO {
    private int diaryIdx;
    private int userIdx;
    private Date creationDate;
    private String content;
//    private String picture;
//    private int music;
//    private boolean isDeleted;
//    private Date deleteDate;
//    private Date expireDate;
    private String emo;

    public DiaryResponseDTO(DiaryEntity diary) {
        this.diaryIdx = diary.getDiaryIdx();
        this.userIdx=diary.getUser().getUserIdx();
        this.creationDate = diary.getCreationDate();
        this.content = diary.getContent();
//        this.picture = diary.getPainting().getPicturePath();
//        this.music = diary.getMusic().getMusicIdx();
        this.emo = diary.getEmo();
    }

}