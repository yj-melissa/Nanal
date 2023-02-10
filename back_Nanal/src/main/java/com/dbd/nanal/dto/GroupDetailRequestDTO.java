package com.dbd.nanal.dto;


import com.dbd.nanal.model.GroupDetailEntity;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private int groupImgIdx;
    private LocalDateTime creationDate;
    private List<String> tags;

    public GroupDetailEntity toEntity() {
        return GroupDetailEntity.builder().groupName(groupName).groupImgIdx(groupImgIdx).isPrivate(isPrivate).groupIdx(groupIdx).build();
    }

}
