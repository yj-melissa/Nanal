package com.dbd.nanal.dto;

import lombok.Getter;

@Getter
public class DiaryCommentRequestDTO {
    private int commentIdx;
    private int userIdx;
    private int diaryIdx;
    private int groupIdx;
    private String content;
}
