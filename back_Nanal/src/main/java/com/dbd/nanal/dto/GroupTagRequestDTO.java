package com.dbd.nanal.dto;

import com.dbd.nanal.model.GroupDetailEntity;
import com.dbd.nanal.model.GroupTagEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class GroupTagRequestDTO {
    GroupDetailEntity groupDetail;
    private int tagIdx;
    private String tag;

    public GroupTagEntity toEntity() {
        return GroupTagEntity.builder().groupDetail(groupDetail).tagIdx(tagIdx).tag(tag).build();
    }
}
