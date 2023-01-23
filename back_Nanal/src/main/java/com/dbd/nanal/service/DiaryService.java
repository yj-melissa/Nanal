package com.dbd.nanal.service;

import com.dbd.nanal.dto.DiaryResponseDTO;
import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(DiaryRepository diaryRepository){
        this.diaryRepository=diaryRepository;
    }

    // write diary
    public DiaryEntity save(DiaryEntity diary){
        diaryRepository.save(diary);
        return diary;
    }

    // get diary
    public DiaryResponseDTO getDiary(int diaryIdx){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(diaryIdx);
        return new DiaryResponseDTO(diaryEntity);
    }

    // delete diary
    public void deleteDiary(int diaryIdx){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(diaryIdx);

        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 30);
        Date date=cal.getTime();

        diaryEntity.deleteDiary(true, new Date(),date );
        diaryRepository.save(diaryEntity);
    }

    //get diary list
    public List<DiaryResponseDTO> diaryList(int groupId){
        List<DiaryEntity> diaryEntityList= diaryRepository.findGroupDiaryList(groupId);
        return diaryEntityList.stream().map(x-> new DiaryResponseDTO(x)).collect(Collectors.toList());
    }
}