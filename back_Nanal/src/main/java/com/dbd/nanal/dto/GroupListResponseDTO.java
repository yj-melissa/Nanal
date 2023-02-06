package com.dbd.nanal.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class GroupListResponseDTO {
    // g.groupIdx, g.groupName, g.imgUrl, d.creationDate
    private final int groupIdx;
    private final String groupName;
    private final String imgUrl;
    private final Date creationDate;

    public GroupListResponseDTO(int groupIdx, String groupName, String imgUrl, Date creationDate) {
        this.groupIdx = groupIdx;
        this.groupName = groupName;
        this.imgUrl = imgUrl;
        this.creationDate = creationDate;
    }
}
