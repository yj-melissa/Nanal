package com.dbd.nanal.dto;

import com.dbd.nanal.model.GroupDetailEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GroupDetailResponseDTO {

    private int groupIdx;
    private String groupName;
    private boolean isPrivate;
    private int groupImgIdx;
    private Date creationTime;

    public GroupDetailResponseDTO(GroupDetailEntity groupEntity) {
        this.groupIdx = groupEntity.getGroupIdx();
        this.groupName = groupEntity.getGroupName();
        this.isPrivate = groupEntity.getIsPrivate();
        this.groupImgIdx = groupEntity.getGroupImgIdx();
        this.creationTime = groupEntity.getCreationDate();
    }


}
