package com.dbd.nanal.service;

import com.dbd.nanal.dto.DiaryResponseDTO;
import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;

    // write diary
    public DiaryEntity save(DiaryEntity diary){
        diaryRepository.save(diary);
        return diary;
    }

    // get diary
    public DiaryResponseDTO getDiary(int diaryIdx){
//        System.out.println(diaryRepository.getById(diaryIdx).getContent());
        return new DiaryResponseDTO(diaryRepository.getById(diaryIdx));
    }

    //get diary list
//    public List<DiaryEntity> diaryList(int groupId){
//        return diaryRepository.findGroupDiaryList(groupId);
//    }
}
