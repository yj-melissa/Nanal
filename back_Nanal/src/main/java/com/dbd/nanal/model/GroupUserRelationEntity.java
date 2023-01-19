package com.dbd.nanal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "group_user_relation")
@Getter
@NoArgsConstructor
public class GroupUserRelationEntity {
    @Id
    @Column(name = "group_user_idx")
    private int groupUserIdx;

    @Column(name = "is_notice")
    private boolean isNotice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_idx")
    private GroupMemberEntity groupMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private UserEntity user;


}
