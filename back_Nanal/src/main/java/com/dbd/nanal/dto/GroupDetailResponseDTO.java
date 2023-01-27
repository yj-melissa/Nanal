package com.dbd.nanal.dto;

import com.dbd.nanal.model.GroupDetailEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GroupDetailResponseDTO {

    private int groupIdx;
    private String groupName;
    private boolean isPrivate;
    private String groupImg;
    private Date creationTime;
    private List<String> tags;

    public GroupDetailResponseDTO(GroupDetailEntity groupEntity) {
        this.groupIdx = groupEntity.getGroupIdx();
        this.groupName = groupEntity.getGroupName();
        this.isPrivate = groupEntity.getIsPrivate();
        this.groupImg = groupEntity.getGroupImg();
        this.creationTime = groupEntity.getCreationDate();


        // tag insert할 때 사용
        if (groupEntity.getGroupTags() != null) {
            int size = groupEntity.getGroupTags().size();

            System.out.println("tag 개수 : " + size);

            this.tags = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                this.tags.add(i, groupEntity.getGroupTags().get(i).getTag());
            }
        }
    }


}
