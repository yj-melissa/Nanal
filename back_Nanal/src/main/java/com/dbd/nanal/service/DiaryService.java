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
    private final GroupDiaryRelationRepository groupDiaryRelationRepository;
    private final DiaryCommentRepository diaryCommentRepository;
    private final BookmarkRepository bookmarkRepository;

    // write diary
    public DiaryResponseDTO save(DiaryRequestDTO diary){
        DiaryEntity diaryEntity=DiaryEntity.builder().creationDate(diary.getCreationDate())
                .user(UserEntity.builder().userIdx(diary.getUserIdx()).build())
                .content(diary.getContent())
                .emo(diary.getEmo()).build();
        return new DiaryResponseDTO(diaryRepository.save(diaryEntity));
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

    // get date diary list
    public List<DiaryResponseDTO> getDateDiaryList(Date findDate){
        List<DiaryEntity> diaryEntityList=diaryRepository.findDateDiaryList(findDate);
        return diaryEntityList.stream().map(x->new DiaryResponseDTO(x)).collect(Collectors.toList());
    }

    // get Month diary list
    public List<DiaryResponseDTO> getMonthDiaryList(String findDate){
        List<DiaryEntity> diaryEntityList=diaryRepository.findMonthDiaryList(Integer.parseInt(findDate.substring(0,4)), Integer.parseInt(findDate.substring(5,7)));
        return diaryEntityList.stream().map(x->new DiaryResponseDTO(x)).collect(Collectors.toList());
    }

    // save diary-group
    public void saveDiaryGroup(GroupDiaryRelationDTO groupDiaryRelationDTO){
        GroupDiaryRelationEntity groupDiaryRelationEntity=GroupDiaryRelationEntity.builder()
                .diary(DiaryEntity.builder().diaryIdx(groupDiaryRelationDTO.getDiaryIdx()).build())
                .groupDetail(GroupDetailEntity.builder().groupIdx(groupDiaryRelationDTO.getGroupIdx()).build())
                .build();
        groupDiaryRelationRepository.save(groupDiaryRelationEntity);
    }

    // save diary comment
    public DiaryCommentResponseDTO saveComment(DiaryCommentRequestDTO diaryCommentRequestDTO){
        DiaryCommentEntity diaryCommentEntity=DiaryCommentEntity.builder().content(diaryCommentRequestDTO.getContent())
                .user(UserEntity.builder().userIdx(diaryCommentRequestDTO.getUserIdx()).build())
                .diary(DiaryEntity.builder().diaryIdx(diaryCommentRequestDTO.getDiaryIdx()).build())
                .groupDetail(GroupDetailEntity.builder().groupIdx(diaryCommentRequestDTO.getGroupIdx()).build())
                .build();
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

    // save bookmark
    public void saveBookmark(BookmarkRequestDTO bookmarkRequestDTO){
        ScrapEntity scrapEntity=ScrapEntity.builder()
                .user(UserEntity.builder().userIdx(bookmarkRequestDTO.getUserIdx()).build())
                .diary(DiaryEntity.builder().diaryIdx(bookmarkRequestDTO.getDiaryIdx()).build())
                .build();
        bookmarkRepository.save(scrapEntity);
    }

    // get bookmark list
    public List<BookmarkResponseDTO> getBookmarkList(int userIdx){
        List<ScrapEntity> scrapEntityList=bookmarkRepository.findByUser(UserEntity.builder().userIdx(userIdx).build());
        return scrapEntityList.stream().map(x-> new BookmarkResponseDTO(x)).collect(Collectors.toList());
    }

    // delete bookmark
    public void deleteBookmark(int bookmarkIdx){
        bookmarkRepository.deleteById(bookmarkIdx);
    }
}