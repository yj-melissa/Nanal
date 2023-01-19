package com.dbd.nanal.dto;

import com.dbd.nanal.model.GroupMemberEntity;
import lombok.Getter;

import java.util.Date;

@Getter
public class GroupResponseDTO {

    private final int groupIdx;
    private final String groupName;
    private final boolean isPrivate;
    private final String groupImg;
    private final Date creationTime;

    public GroupResponseDTO(GroupMemberEntity groupEntity) {
        this.groupIdx = groupEntity.getGroupIdx();
        this.groupName = groupEntity.getGroupName();
        this.isPrivate = groupEntity.getIsPrivate();
        this.groupImg = groupEntity.getGroupImg();
        this.creationTime = groupEntity.getCreationDate();
    }
}
