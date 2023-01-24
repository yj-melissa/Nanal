package com.dbd.nanal.service;

import com.dbd.nanal.dto.DiaryResponseDTO;
import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    // write diary
    public DiaryResponseDTO save(DiaryEntity diary){
        return new DiaryResponseDTO(diaryRepository.save(diary));
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

    // update diary
    public void updateDiary(DiaryEntity diary){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(diary.getDiaryIdx());

        diaryEntity.updateDiary(diary.getCreationDate(), diary.getContent(), diary.getPainting(), diary.getMusic(), diary.getEmo());
        diaryRepository.save(diaryEntity);
    }

    //get diary list
    public List<DiaryResponseDTO> diaryList(int groupId){
        List<DiaryEntity> diaryEntityList= diaryRepository.findGroupDiaryList(groupId);
        return diaryEntityList.stream().map(x-> new DiaryResponseDTO(x)).collect(Collectors.toList());
    }
}