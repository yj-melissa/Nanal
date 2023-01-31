package com.dbd.nanal.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="keyword")
public class KeywordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="keyword_idx")
    private int keywordIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_idx", referencedColumnName = "diary_idx")
    private DiaryEntity diary;

    private String keyword;

    @Builder
    public KeywordEntity(int keywordIdx, DiaryEntity diary, String keyword) {
        this.keywordIdx=keywordIdx;
        this.diary=diary;
        this.keyword=keyword;
    }

}
