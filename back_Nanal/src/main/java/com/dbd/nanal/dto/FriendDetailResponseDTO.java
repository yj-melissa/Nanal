package com.dbd.nanal.dto;

import com.dbd.nanal.model.UserProfileEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendDetailResponseDTO {

    private final int userIdx;
    private final String nickname;
    private final String img;
    private final String Introduction;


    public FriendDetailResponseDTO(UserProfileEntity profile, int userIdx) {
        this.userIdx = userIdx;
        this.nickname = profile.getNickname();
        this.Introduction = profile.getIntroduction();
        this.img = profile.getImg();
    }


}
