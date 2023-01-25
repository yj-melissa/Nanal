package com.dbd.nanal.dto;

import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.model.MusicEntity;
import com.dbd.nanal.model.PaintingEntity;
import com.dbd.nanal.model.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiaryRequestDTO {
    private int diaryIdx;
    private int userIdx;
    private Date creationDate;
    private String content;
    private int picture;
    private int music;
    private String emo;

    private  int groupIdx;

//    @Builder
//    public DiaryRequestDTO(int diaryIdx, UserEntity user, Timestamp creationDate, String content, PaintingEntity picture, MusicEntity music, boolean isDeleted, Timestamp deleteDate, Timestamp expireDate, String emo) {
//        this.diaryIdx = diaryIdx;
//        this.user = user;
//        this.creationDate = creationDate;
//        this.content = content;
//        this.picture = picture;
//        this.music = music;
//        this.isDeleted = isDeleted;
//        this.deleteDate = deleteDate;
//        this.expireDate = expireDate;
//        this.emo = emo;
//    }

    public DiaryEntity toEntity(UserEntity user){
        return DiaryEntity.builder()
                .creationDate(creationDate)
                .diaryIdx(diaryIdx)
                .user(user)
                .content(content)
//                .painting(picture)
//                .music(music)
                .emo(emo)
                .build();
    }

    public DiaryEntity toEntity(){
        return DiaryEntity.builder()
                .creationDate(creationDate)
                .diaryIdx(diaryIdx)
                .content(content)
//                .painting(picture)
//                .music(music)
                .emo(emo)
                .build();
    }
}