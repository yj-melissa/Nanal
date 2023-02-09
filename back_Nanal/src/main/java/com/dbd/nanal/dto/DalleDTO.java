package com.dbd.nanal.dto;


import lombok.Getter;

@Getter
public class DalleDTO {
    
    String content;
    String key;

    public DalleDTO(String content, String key) {
        this.content = content;
        this.key = key;
    }
}
