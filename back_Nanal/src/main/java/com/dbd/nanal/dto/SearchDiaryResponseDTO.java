package com.dbd.nanal.dto;

import com.dbd.nanal.model.DiaryEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchDiaryResponseDTO {
    private int diaryIdx;
    private int userIdx;
    private LocalDateTime creationDate;
    private String content;
    private String nickname;
    private int groupIdx;
    private String groupName;

    public SearchDiaryResponseDTO(DiaryEntity diary) {
        this.diaryIdx = diary.getDiaryIdx();
        this.creationDate = diary.getCreationDate();
        this.content = diary.getContent();
        this.userIdx = diary.getUser().getUserIdx();
        this.nickname = diary.getUser().getUserProfile().getNickname();
    }
}
