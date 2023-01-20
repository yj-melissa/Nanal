package com.dbd.nanal.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name="notice")
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notice_idx")
    private int noticeIdx;

    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private UserEntity user;

    @Column(name="notice_type")
    private int noticeType;

    private String content;

    @Column(name="is_checked")
    private Boolean isChecked;

    @CreationTimestamp
    @Column(name="creation_date")
    private Timestamp creationDate;

    @Column(name="expire_date")
    private Timestamp expireDate;

    @Builder
    public NoticeEntity(int noticeIdx, UserEntity user, int noticeType, String content, Boolean isChecked, Timestamp creationDate, Timestamp expireDate) {
        this.noticeIdx=noticeIdx;
        this.user=user;
        this.noticeType=noticeType;
        this.content=content;
        this.isChecked=isChecked;
        this.creationDate=creationDate;
        this.expireDate=expireDate;
    }
}
