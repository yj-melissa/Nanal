package com.dbd.nanal.dto;

import com.dbd.nanal.model.GroupTagEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GroupTagResponseDTO {

//    private final List<String> tags;
    private int tagIdx;
    private String tag;

    public GroupTagResponseDTO(GroupTagEntity groupTagEntity) {

        // {tagIdx, tag} 리스트 리턴
        //
//        this.tags = new ArrayList<>();
//        for (GroupTagEntity groupTagEntity : groupTagEntities) {
//            this.tags.add(groupTagEntity.getTag());
//        }
        this.tagIdx = groupTagEntity.getTagIdx();
        this.tag = groupTagEntity.getTag();
    }


}
