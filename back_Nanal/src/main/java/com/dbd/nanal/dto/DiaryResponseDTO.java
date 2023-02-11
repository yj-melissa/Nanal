package com.dbd.nanal.dto;

import com.dbd.nanal.model.DiaryEntity;
import lombok.Getter;

import java.util.Date;

@Getter
public class DiaryResponseDTO {
    private int diaryIdx;
    private int userIdx;
    private Date diaryDate;
    private String content;
    private String nickname;
    private String picture;
//    private int music;
//    private boolean isDeleted;
//    private Date deleteDate;
//    private Date expireDate;
    private String emo;

    public DiaryResponseDTO(DiaryEntity diary) {
        this.diaryIdx = diary.getDiaryIdx();
        this.userIdx=diary.getUser().getUserIdx();
        this.diaryDate = diary.getDiaryDate();
        this.content = diary.getContent();
        this.picture = diary.getPainting().getImgUrl();
//        this.music = diary.getMusic().getMusicIdx();
        this.emo = diary.getEmo();
        this.nickname=diary.getUser().getUserProfile().getNickname();
    }

}