package com.dbd.nanal.dto;

import com.dbd.nanal.model.GroupTagEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GroupTagResponseDTO {

    private final List<String> tags;

    public GroupTagResponseDTO(List<GroupTagEntity> groupTagEntities) {

        // tag 리스트만 리턴
        this.tags = new ArrayList<>();
        for (GroupTagEntity groupTagEntity : groupTagEntities) {
            this.tags.add(groupTagEntity.getTag());
        }
    }

}
