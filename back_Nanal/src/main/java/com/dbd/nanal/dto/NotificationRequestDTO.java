package com.dbd.nanal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotificationRequestDTO {
    private List<Integer> userIdx;
    private int request_user_idx;
    private int request_group_idx;
    private int notice_type;
    private int request_diary_idx;
}
