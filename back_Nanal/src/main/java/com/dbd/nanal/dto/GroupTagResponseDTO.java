package com.dbd.nanal.dto;

import com.dbd.nanal.model.GroupTagEntity;
import lombok.Getter;

@Getter
public class GroupTagResponseDTO {

    private final int tagIdx;
    private final String tag;

    public GroupTagResponseDTO(GroupTagEntity groupTagEntity) {
        this.tagIdx = groupTagEntity.getTagIdx();
        this.tag = groupTagEntity.getTag();
    }


}
