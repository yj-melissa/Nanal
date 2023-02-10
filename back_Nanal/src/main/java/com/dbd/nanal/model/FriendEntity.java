package com.dbd.nanal.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "friend")
public class FriendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private int friend_idx;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user_idx1;

    @ManyToOne
    @JoinColumn(name = "user_idx2")
    private UserEntity user_idx2;

    @Builder
    public FriendEntity(int friend_idx, UserEntity user_idx1, UserEntity user_idx2) {
        this.friend_idx = friend_idx;
        this.user_idx1 = user_idx1;
        this.user_idx2 = user_idx2;
    }

}
