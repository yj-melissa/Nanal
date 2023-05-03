package com.dbd.nanal.dto;

import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.model.PaintingEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DiaryRequestDTO {
    private int diaryIdx;
    private int userIdx;
    private LocalDateTime creationDate;
    private Date diaryDate;
    private String content;
    private PaintingEntity painting;
    private int music;
    private String emo;
    private List<Integer> groupIdxList;
    private String imgUrl;

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

    public DiaryEntity toEntity() {
        return DiaryEntity.builder()
                .diaryDate(diaryDate)
                .diaryIdx(diaryIdx)
                .content(content)
                .painting(painting)
//                .music(music)
                .emo(emo)
                .imgUrl(imgUrl)
                .build();
    }
}