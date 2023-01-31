package com.dbd.nanal.model;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "scrap")
public class ScrapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="scrap_idx", columnDefinition = "INT UNSIGNED")
    private int scrapIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_idx")
    private DiaryEntity diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private UserEntity user;

    @Builder
    public ScrapEntity(DiaryEntity diary, UserEntity user) {
        this.diary=diary;
        this.user=user;
    }
}
