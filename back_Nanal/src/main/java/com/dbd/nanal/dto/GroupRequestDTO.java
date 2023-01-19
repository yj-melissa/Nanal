package com.dbd.nanal.dto;


import com.dbd.nanal.model.GroupMemberEntity;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
//@Getter
//@Setter
public class GroupRequestDTO {

    @NotNull
    private int groupIdx;
    private String groupName;
    private boolean isPrivate;
    private String groupImg;
    private Date creationTime;

    @Builder
    public GroupRequestDTO(int groupIdx, String groupName, boolean isPrivate, String groupImg, Date creationTime) {
        this.groupIdx = groupIdx;
        this.groupName = groupName;
        this.isPrivate = isPrivate;
        this.groupImg = groupImg;
        this.creationTime = creationTime;
    }

    public GroupMemberEntity toEntity() {
        System.out.println("isPrivate : " + isPrivate);
        return GroupMemberEntity.builder().groupName(groupName).groupImg(groupImg).isPrivate(isPrivate).build();
    }
}
