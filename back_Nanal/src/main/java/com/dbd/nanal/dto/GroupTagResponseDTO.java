package com.dbd.nanal.dto;

import com.dbd.nanal.model.GroupTagEntity;

public class GroupTagResponseDTO {

    private int tagIdx;

    private int groupIdx;

    private String tag;

    public GroupTagResponseDTO(GroupTagEntity groupTag){
        this.tagIdx = groupTag.getTagIdx();
        this.groupIdx = groupTag.getGroupMember().getGroupIdx();
        this.tag = groupTag.getTag();
    }
}
