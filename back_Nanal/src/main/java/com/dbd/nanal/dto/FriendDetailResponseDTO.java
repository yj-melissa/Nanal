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
    private final String shortContent;

    public FriendDetailResponseDTO(UserProfileEntity profile, int userIdx, String shortContent) {

        this.userIdx = userIdx;
        this.nickname = profile.getNickname();
        this.Introduction = profile.getIntroduction();
        this.img = profile.getImg();
        // 몇 자 보여줄까?!??!!
        this.shortContent = shortContent.substring(0,Math.min(shortContent.length(), 50));
    }

    public FriendDetailResponseDTO(UserProfileEntity profile, int userIdx) {
        this.userIdx = userIdx;
        this.nickname = profile.getNickname();
        this.Introduction = profile.getIntroduction();
        this.img = profile.getImg();
        this.shortContent = null;
    }
//    public FriendDetailResponseDTO(int userIdx, String nickname, String img) {
//        this.userIdx = userIdx;
//        this.nickname = ni
//        this.Introduction = profile.getIntroduction();
//        this.img =img;
//    }

}
