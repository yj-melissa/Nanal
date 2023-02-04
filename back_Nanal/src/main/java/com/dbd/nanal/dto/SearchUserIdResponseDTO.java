package com.dbd.nanal.dto;

import com.dbd.nanal.model.UserEntity;
import lombok.Getter;

@Getter
public class SearchUserIdResponseDTO {
    private int userIdx;
    private String name;
    private String email;
    private String userId;

    public SearchUserIdResponseDTO(UserEntity user){
        this.userIdx=user.getUserIdx();
        this.name=user.getName();
        this.email=user.getEmail();
        this.userId=user.getUserId();
    }
}
