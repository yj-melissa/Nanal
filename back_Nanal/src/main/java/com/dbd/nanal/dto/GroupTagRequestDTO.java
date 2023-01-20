package com.dbd.nanal.dto;

import com.dbd.nanal.model.GroupMemberEntity;
import com.dbd.nanal.model.GroupTagEntity;
import lombok.Builder;
import lombok.Getter;


@Getter
public class GroupTagRequestDTO {
    private int tagIdx;

    private int groupMemberIdx;

    private String tag;

    @Builder
    public GroupTagRequestDTO(int tagIdx, int groupMemberIdx, String tag) {
        this.tagIdx = tagIdx;
        this.groupMemberIdx = groupMemberIdx;
        this.tag = tag;
    }

    public GroupTagEntity toEntity(GroupMemberEntity groupMember){
        return GroupTagEntity.builder().tagIdx(tagIdx).groupMember(groupMember).tag(tag).build();
    }
}
