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
    private final KeywordRepository keywordRepository;

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

        diaryEntity.setDeleted(true);
        diaryEntity.setDeleteDate(new Date());
        diaryEntity.setExpireDate(date);

        diaryRepository.save(diaryEntity);
    }

    // update diary
    public DiaryResponseDTO updateDiary(DiaryEntity diary){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(diary.getDiaryIdx());

        diaryEntity.setCreationDate(diaryEntity.getCreationDate());
        diaryEntity.setContent(diary.getContent());
        diaryEntity.setPainting(diary.getPainting());
        diaryEntity.setMusic(diary.getMusic());
        diaryEntity.setEmo(diary.getEmo());

        return new DiaryResponseDTO(diaryRepository.save(diaryEntity));
    }

    //get diary list
    public List<DiaryResponseDTO> diaryList(int groupId){
        List<DiaryEntity> diaryEntityList= diaryRepository.findGroupDiaryList(groupId);
        return diaryEntityList.stream().map(x-> new DiaryResponseDTO(x)).collect(Collectors.toList());
    }

    // get date diary list
    public List<DiaryResponseDTO> getDateDiaryList(String findDate, int userIdx){
//    public List<DiaryResponseDTO> getDateDiaryList(Date findDate){
//        List<DiaryEntity> diaryEntityList=diaryRepository.findDateDiaryList(findDate);
        List<DiaryEntity> diaryEntityList=diaryRepository.findDateDiaryList(Integer.parseInt(findDate.substring(0,4)), Integer.parseInt(findDate.substring(5,7)), Integer.parseInt(findDate.substring(8,10)), userIdx);
        return diaryEntityList.stream().map(x->new DiaryResponseDTO(x)).collect(Collectors.toList());
    }

    // get Month diary list
    public List<DiaryResponseDTO> getMonthDiaryList(String findDate, int userIdx){
        List<DiaryEntity> diaryEntityList=diaryRepository.findMonthDiaryList(Integer.parseInt(findDate.substring(0,4)), Integer.parseInt(findDate.substring(5,7)), userIdx);
        return diaryEntityList.stream().map(x->new DiaryResponseDTO(x)).collect(Collectors.toList());
    }

    // get user diary list
    public List<DiaryResponseDTO> userDiaryList(int userIdx){
        List<DiaryEntity> diaryEntityList=diaryRepository.findByUser(UserEntity.builder().userIdx(userIdx).build());
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

    // delete diary-group
    public void deleteDiaryGroup(int diaryIdx){
        List<GroupDiaryRelationEntity> groupDiaryRelationEntityList=groupDiaryRelationRepository.findByDiary(DiaryEntity.builder().diaryIdx(diaryIdx).build());
        groupDiaryRelationRepository.deleteAll(groupDiaryRelationEntityList);
    }

    // return diary count
    public Long getDiaryCount(int userIdx){
        return diaryRepository.countByUser(UserEntity.builder().userIdx(userIdx).build());
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
        diaryCommentEntity.setContent(diaryCommentRequestDTO.getContent());
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

        diaryEntity.setDeleted(false);
        diaryEntity.setDeleteDate(null);
        diaryEntity.setExpireDate(null);

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

    // return bookmark count
    public Long getBookCount(int userIdx){
        return bookmarkRepository.countByUser(UserEntity.builder().userIdx(userIdx).build());
    }

    // save keyword
    public void saveKeyword(int diaryIdx, List<String> keywords){
        List<KeywordEntity> keywordEntityList=keywordRepository.findByDiary(DiaryEntity.builder().diaryIdx(diaryIdx).build());
        // update keyword
        if(keywordEntityList.size()!=0){
            for(int i=0; i<keywordEntityList.size(); i++){
                keywordEntityList.get(i).setKeyword(keywords.get(i));
                keywordRepository.save(keywordEntityList.get(i));
            }
            return;
        }
        for(String keyword: keywords){
            KeywordEntity keywordEntity=KeywordEntity.builder().diary(DiaryEntity.builder().diaryIdx(diaryIdx).build())
                    .keyword(keyword).build();
            keywordRepository.save(keywordEntity);
        }
    }
}