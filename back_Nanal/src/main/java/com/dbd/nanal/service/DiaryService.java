package com.dbd.nanal.service;

import com.dbd.nanal.dto.DiaryResponseDTO;
import com.dbd.nanal.dto.GroupDiaryRelationDTO;
import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.model.GroupDetailEntity;
import com.dbd.nanal.model.GroupDiaryRelationEntity;
import com.dbd.nanal.repository.DiaryRepository;
import com.dbd.nanal.repository.GroupDiaryRelationRepository;
import com.dbd.nanal.repository.GroupRepository;
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
    private final GroupRepository groupRepository;
    private final GroupDiaryRelationRepository groupDiaryRelationRepository;

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

    // save diary-group
    public void saveDiaryGroup(GroupDiaryRelationDTO groupDiaryRelationDTO){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(groupDiaryRelationDTO.getDiaryIdx());
        GroupDetailEntity groupDetailEntity=groupRepository.getReferenceById(groupDiaryRelationDTO.getGroupIdx());
        GroupDiaryRelationEntity groupDiaryRelationEntity=new GroupDiaryRelationEntity(diaryEntity, groupDetailEntity);
        groupDiaryRelationRepository.save(groupDiaryRelationEntity);
    }
}