package com.dbd.nanal.service;

import com.dbd.nanal.dto.*;
import com.dbd.nanal.model.*;
import com.dbd.nanal.repository.*;
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
    private final UserRepository userRepository;
    private final DiaryCommentRepository diaryCommentRepository;

    // write diary
    public DiaryResponseDTO save(DiaryRequestDTO diary){
        UserEntity user=userRepository.getReferenceById(diary.getUserIdx());
        return new DiaryResponseDTO(diaryRepository.save(diary.toEntity(user)));
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

        diaryEntity.flagDiary(true, new Date(),date );
        diaryRepository.save(diaryEntity);
    }

    // update diary
    public DiaryResponseDTO updateDiary(DiaryEntity diary){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(diary.getDiaryIdx());
        diaryEntity.updateDiary(diary.getCreationDate(), diary.getContent(), diary.getPainting(), diary.getMusic(), diary.getEmo());
        return new DiaryResponseDTO(diaryRepository.save(diaryEntity));
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

    // save diary comment
    public DiaryCommentResponseDTO saveComment(DiaryCommentRequestDTO diaryCommentRequestDTO){
        UserEntity user=userRepository.getReferenceById(diaryCommentRequestDTO.getUserIdx());
        DiaryEntity diary=diaryRepository.getReferenceById(diaryCommentRequestDTO.getDiaryIdx());
        GroupDetailEntity group=groupRepository.getReferenceById(diaryCommentRequestDTO.getGroupIdx());
        DiaryCommentEntity diaryCommentEntity=DiaryCommentEntity.builder().content(diaryCommentRequestDTO.getContent()).user(user).diary(diary).groupDetail(group).build();
        return new DiaryCommentResponseDTO(diaryCommentRepository.save(diaryCommentEntity));
    }

    // update diary comment
    public DiaryCommentResponseDTO updateComment(DiaryCommentRequestDTO diaryCommentRequestDTO){
        DiaryCommentEntity diaryCommentEntity=diaryCommentRepository.getReferenceById(diaryCommentRequestDTO.getCommentIdx());
        diaryCommentEntity.updateComment(diaryCommentRequestDTO.getContent());
        return new DiaryCommentResponseDTO(diaryCommentRepository.save(diaryCommentEntity));
    }

    // get diary comment List
    public List<DiaryCommentResponseDTO> getDiaryCommentList(int groupIdx, int diaryIdx){
        List<DiaryCommentEntity> diaryCommentEntityList= diaryCommentRepository.findGroupDiaryCommentList(groupIdx, diaryIdx);
        return diaryCommentEntityList.stream().map(x-> new DiaryCommentResponseDTO(x)).collect(Collectors.toList());
    }

    // delete diary comment
    public void deleteDiaryComment(int commentIdx){
        diaryCommentRepository.deleteById(commentIdx);
    }

    // TrashBin
    // get TrashBin Diary List
    public List<DiaryResponseDTO> getTrashDiary(int userIdx){
        List<DiaryEntity> diaryEntityList=diaryRepository.findByUserIdx(userIdx);
        return diaryEntityList.stream().map(x-> new DiaryResponseDTO(x)).collect(Collectors.toList());
    }

    // delete TrashBin
    public void deleteTrashBin(int userIdx){
        List<DiaryEntity> diaryEntityList=diaryRepository.findByUserIdx(userIdx);
        diaryRepository.deleteAll(diaryEntityList);
    }

    // get back diary
    public DiaryResponseDTO reDiary(int diaryIdx){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(diaryIdx);
        diaryEntity.flagDiary(false, null, null );
        return new DiaryResponseDTO(diaryRepository.save(diaryEntity));
    }
}