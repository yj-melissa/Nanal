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


    // User service 호출해서 UserEntity 채우기
    private UserEntity userEntity;

    // Group service 호출해서 GroupEntity 채우기
    private GroupDetailEntity groupDetail;

    public GroupUserRelationRequestDTO(int userIdx, int groupIdx) {
        this.userIdx = userIdx;
        this.groupIdx = groupIdx;
    }

    public GroupUserRelationEntity toEntity() {
        return GroupUserRelationEntity.builder().user(userEntity).groupDetail(groupDetail).build();
    }


}
