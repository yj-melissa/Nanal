package com.dbd.nanal.dto;

import lombok.Getter;

@Getter
public class NotificationRequestDTO {
    private int userIdx;
    private int request_user_idx;
    private int request_group_idx;
    private int notice_type;
    private int request_diary_idx;
}
