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
    private String imgUrl;

    public GroupDetailResponseDTO(GroupDetailEntity groupEntity) {
        this.groupIdx = groupEntity.getGroupIdx();
        this.groupName = groupEntity.getGroupName();
        this.imgUrl = groupEntity.getImgUrl();
    }

}
