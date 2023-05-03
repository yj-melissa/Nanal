package com.dbd.nanal.dto;

import com.dbd.nanal.model.GroupUserRelationEntity;
import lombok.Getter;

@Getter
public class GroupUserRelationResponseDTO {

    private final int groupIdx;

    public GroupUserRelationResponseDTO(GroupUserRelationEntity groupUserRelationEntity) {
        this.groupIdx = groupUserRelationEntity.getGroupDetail().getGroupIdx();
    }
}
