package com.dbd.nanal.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(columnDefinition = "INT UNSIGNED")
    private int groupDiaryIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_idx")
    @JsonIgnore
    private DiaryEntity diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_idx")
    @JsonIgnore
    private GroupDetailEntity groupDetail;


    @Builder
    public GroupDiaryRelationEntity(DiaryEntity diary, GroupDetailEntity groupDetail) {
        this.diary = diary;
        this.groupDetail = groupDetail;
    }

}
