package com.dbd.nanal.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "group_user_relation")
@Getter
@NoArgsConstructor
public class GroupUserRelationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment
    @Column(name = "group_user_idx")
    private int groupUserIdx;

    @Column(name = "is_notice")
    private boolean isNotice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_idx")
    private GroupDetailEntity groupDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private UserEntity user;

    @Builder
    public GroupUserRelationEntity(int groupUserIdx, boolean isNotice, GroupDetailEntity groupDetail, UserEntity user) {
        this.groupUserIdx = groupUserIdx;
        this.isNotice = isNotice;
        this.groupDetail = groupDetail;
        this.user = user;
    }
}
