package com.dbd.nanal.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "group_diary")
@Getter
@NoArgsConstructor
public class GroupDiaryRelationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment
    @Column(name = "group_diary_idx")
    private int group_diary_idx;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_idx")
    private DiaryEntity diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_idx")
    private GroupDetailEntity groupDetail;


    @Builder
    public GroupDiaryRelationEntity(int group_diary_idx, DiaryEntity diary, GroupDetailEntity groupDetail) {
        this.group_diary_idx = group_diary_idx;
        this.diary = diary;
        this.groupDetail = groupDetail;
    }
}
