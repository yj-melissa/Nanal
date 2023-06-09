package com.dbd.nanal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(columnDefinition = "INT UNSIGNED")
    private int groupUserIdx;

    @Column(name = "is_notice")
    private boolean isNotice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_idx")
    @JsonIgnore
    private GroupDetailEntity groupDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    @JsonIgnore
    private UserEntity user;

    @Builder
    public GroupUserRelationEntity(int groupUserIdx, boolean isNotice, GroupDetailEntity groupDetail, UserEntity user) {
        this.groupUserIdx = groupUserIdx;
        this.isNotice = isNotice;
        this.groupDetail = groupDetail;
        this.user = user;
    }
}
