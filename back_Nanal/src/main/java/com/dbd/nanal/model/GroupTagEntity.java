package com.dbd.nanal.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "group_tag")
@Getter
@Setter
@NoArgsConstructor
public class GroupTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment
    @Column(name = "tag_idx")
    private int tagIdx;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_idx")
    private GroupDetailEntity groupDetail;

    @Column
    private String tag;

    @Builder
    public GroupTagEntity(int tagIdx, GroupDetailEntity groupDetail, String tag) {
        this.tagIdx = tagIdx;
        this.groupDetail = groupDetail;
        this.tag = tag;
    }

    public void tagUpdate(String tag){
        this.tag = tag;
    }

}
