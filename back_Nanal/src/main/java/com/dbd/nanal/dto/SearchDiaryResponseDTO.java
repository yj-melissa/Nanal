package com.dbd.nanal.dto;

import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.model.GroupDetailEntity;
import lombok.Getter;

import java.util.Date;

@Getter
public class SearchDiaryResponseDTO {
    private int diaryIdx;
    private int userIdx;
    private Date creationDate;
    private String content;
    private String nickName;

//    private int groupIdx;
//    private String groupName;

    public SearchDiaryResponseDTO(DiaryEntity diary){
        this.diaryIdx=diary.getDiaryIdx();
        this.creationDate=diary.getCreationDate();
        this.content=diary.getContent();
        this.userIdx=diary.getUser().getUserIdx();
        this.nickName=diary.getUser().getUserProfile().getNickname();
//        this.groupIdx=group.getGroupIdx();
//        this.groupName=group.getGroupName();
    }
}
