package com.dbd.nanal.dto;

import lombok.Getter;

@Getter
public class GroupDiaryRelationDTO {
    private int groupDiaryIdx;
    private int diaryIdx;
    private int groupIdx;

    public GroupDiaryRelationDTO(int diaryIdx, int groupIdx){
        this.diaryIdx=diaryIdx;
        this.groupIdx=groupIdx;
    }
}
