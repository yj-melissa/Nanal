package com.dbd.nanal.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "emotion")
public class EmotionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emotion_idx", columnDefinition = "INT UNSIGNED")
    private int emotionIdx;

    private String joy; // 기쁨
    private String sad; // 슬픔
    private String emb; // 당황
    private String ang; // 분노
    private String nerv; // 불안
    private String calm; // 평온

    @Builder
    public EmotionEntity(int emotionIdx, String joy, String sad, String emb, String ang, String nerv, String calm) {
        this.emotionIdx = emotionIdx;
        this.joy = joy;
        this.sad = sad;
        this.emb = emb;
        this.ang = ang;
        this.nerv = nerv;
        this.calm = calm;
    }

}
