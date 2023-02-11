package com.dbd.nanal.dto;

public enum Emotion {

    ANG("https://nanal-dbd.s3.ap-northeast-2.amazonaws.com/emotion/emo_ang_s.png"),
    CALM("https://nanal-dbd.s3.ap-northeast-2.amazonaws.com/emotion/emo_calm_s.png"),
    EMB("https://nanal-dbd.s3.ap-northeast-2.amazonaws.com/emotion/emo_emb_s.png"),
    JOY("https://nanal-dbd.s3.ap-northeast-2.amazonaws.com/emotion/emo_joy_s.png"),
    NERV("https://nanal-dbd.s3.ap-northeast-2.amazonaws.com/emotion/emo_nerv_s.png"),
    SAD("https://nanal-dbd.s3.ap-northeast-2.amazonaws.com/emotion/emo_sad_s.png\n");

    private final String imgUrl;

    Emotion(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String randomEmo() {

        Double random = Math.random() * 60; // 0.0~1.0

        if (random < 1) {
            return ANG.imgUrl;
        } else if (random < 2) {
            return CALM.imgUrl;
        } else if (random < 3) {
            return EMB.imgUrl;
        } else if (random < 4) {
            return JOY.imgUrl;
        } else if (random < 5) {
            return NERV.imgUrl;
        } else {
            return SAD.imgUrl;
        }


    }


}
