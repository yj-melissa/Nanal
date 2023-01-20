package com.dbd.nanal.dto;


import com.dbd.nanal.model.GroupMemberEntity;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GroupRequestDTO {

    @NotNull
    private int groupIdx;
    private String groupName;
    private boolean isPrivate;
    private String groupImg;
    private Date creationDate;

    private List<String> tags;

    @Builder
    public GroupRequestDTO(int groupIdx, String groupName, boolean isPrivate, String groupImg, Date creationDate, List<String> tags) {
        this.groupIdx = groupIdx;
        this.groupName = groupName;
        this.isPrivate = isPrivate;
        this.groupImg = groupImg;
        this.creationDate = creationDate;
        this.tags = tags;
    }

    @Builder

    public GroupRequestDTO(int groupIdx, String groupName, boolean isPrivate, String groupImg, Date creationDate) {
        this.groupIdx = groupIdx;
        this.groupName = groupName;
        this.isPrivate = isPrivate;
        this.groupImg = groupImg;
        this.creationDate = creationDate;
    }

    public GroupMemberEntity toEntity() {
        System.out.println("isPrivate : " + isPrivate);
        return GroupMemberEntity.builder().groupName(groupName).groupImg(groupImg).isPrivate(isPrivate).groupIdx(groupIdx).creationDate(creationDate).build();
    }
}
