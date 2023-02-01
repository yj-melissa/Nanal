package com.dbd.nanal.dto;


import com.dbd.nanal.model.GroupDetailEntity;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GroupDetailRequestDTO {

    @NotNull
    private int groupIdx;
    private String groupName;
    private boolean isPrivate;
    private String groupImg;
    private Date creationDate;

    private List<String> tags;

//    @Builder
//    public GroupDetailRequestDTO(int groupIdx, String groupName, boolean isPrivate, String groupImg, Date creationDate, List<String> tags) {
//        this.groupIdx = groupIdx;
//        this.groupName = groupName;
//        this.isPrivate = isPrivate;
//        this.groupImg = groupImg;
//        this.creationDate = creationDate;
//        this.tags = tags;
//    }

    public GroupDetailEntity toEntity() {
        return GroupDetailEntity.builder().groupName(groupName).groupImg(groupImg).isPrivate(isPrivate).groupIdx(groupIdx).creationDate(creationDate).build();
    }
}
