package com.dbd.nanal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryCommentRequestDTO {
    private int commentIdx;
    private int userIdx;
    private int diaryIdx;
    private int groupIdx;
    private String content;
}
