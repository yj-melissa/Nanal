package com.dbd.nanal.dto;


import com.dbd.nanal.model.GroupDetailEntity;
import com.dbd.nanal.model.GroupUserRelationEntity;
import com.dbd.nanal.model.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class GroupUserRelationRequestDTO {

    private int userIdx;
    private int groupIdx;
    private UserEntity userEntity;
    private GroupDetailEntity groupDetail;

    public GroupUserRelationRequestDTO(int userIdx, int groupIdx) {
        this.userIdx = userIdx;
        this.groupIdx = groupIdx;
    }

    public GroupUserRelationEntity toEntity() {
        return GroupUserRelationEntity.builder().user(userEntity).groupDetail(groupDetail).build();
    }

}
