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
    @Column(name="scrap_idx")
    private int scrapIdx;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="diary_idx")
    private DiaryEntity diary;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private UserEntity user;

    @Builder
    public ScrapEntity(int scrapIdx, DiaryEntity diary, UserEntity user) {
        this.scrapIdx=scrapIdx;
        this.diary=diary;
        this.user=user;
    }
}
