package com.dbd.nanal.dto;

import com.dbd.nanal.model.ScrapEntity;
import lombok.Getter;

import java.util.Date;

@Getter
public class BookmarkResponseDTO {
    private int bookmarkIdx;
    private int userIdx;

    //diary
    private int diaryIdx;
    private Date creationDate;
    private String content;
    //    private String picture;
    //    private int music;
    private String emo;


    public BookmarkResponseDTO(ScrapEntity scrapEntity) {
        this.bookmarkIdx=scrapEntity.getScrapIdx();
        this.userIdx=scrapEntity.getUser().getUserIdx();

        this.diaryIdx = scrapEntity.getDiary().getDiaryIdx();
        this.creationDate = scrapEntity.getDiary().getCreationDate();
        this.content = scrapEntity.getDiary().getContent();
        this.emo = scrapEntity.getDiary().getEmo();
    }
}
