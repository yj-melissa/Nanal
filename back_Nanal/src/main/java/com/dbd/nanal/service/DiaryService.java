package com.dbd.nanal.service;

import com.dbd.nanal.dto.*;
import com.dbd.nanal.model.*;
import com.dbd.nanal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
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
    private final UserRepository userRepository;

    // write diary
    public DiaryResponseDTO save(DiaryRequestDTO diary){
        UserEntity user=userRepository.getReferenceById(diary.getUserIdx());
        DiaryEntity diaryEntity=DiaryEntity.builder().creationDate(diary.getCreationDate())
                .user(user)
                .diaryDate(diary.getDiaryDate())
                .content(diary.getContent())
                .emo(diary.getEmo())
                .imgUrl(diary.getImgUrl())
                .build();
        return new DiaryResponseDTO(diaryRepository.save(diaryEntity));
    }

    // get diary
    public DiaryResponseDTO getDiary(int diaryIdx){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(diaryIdx);
        return new DiaryResponseDTO(diaryEntity);
    }

    public boolean isBookmark(int diaryIdx, int userIdx){
        return bookmarkRepository.existsByDiaryAndUser(DiaryEntity.builder().diaryIdx(diaryIdx).build(), UserEntity.builder().userIdx(userIdx).build());
    }

    //get diary group List
    public List<Integer> getGroupList(int diaryIdx){
        List<Integer> groupList=new ArrayList<>();
        List<GroupDiaryRelationEntity> groupDiaryRelationEntityList=groupDiaryRelationRepository.findByDiary(DiaryEntity.builder().diaryIdx(diaryIdx).build());
        for(GroupDiaryRelationEntity groupDiaryRelationEntity: groupDiaryRelationEntityList){
            groupList.add(groupDiaryRelationEntity.getGroupDetail().getGroupIdx());
        }
        return groupList;
    }

    // delete diary
    public void deleteDiary(int diaryIdx){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(diaryIdx);

        Calendar cal=Calendar.getInstance();
        cal.setTime(new java.sql.Date(System.currentTimeMillis()));
        cal.add(Calendar.DATE, 30);
        long date=cal.getTimeInMillis();

        diaryEntity.setDeleted(true);
        diaryEntity.setDeleteDate(new java.sql.Date(System.currentTimeMillis()));
        diaryEntity.setExpireDate(new Date(date));

        diaryRepository.save(diaryEntity);
    }

    // update diary
    public DiaryResponseDTO updateDiary(DiaryEntity diary){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(diary.getDiaryIdx());

        diaryEntity.setCreationDate(diaryEntity.getCreationDate());
        diaryEntity.setDiaryDate(diary.getDiaryDate());
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
    public List<DiaryResponseDTO> getDateDiaryList(Date findDate, int userIdx){
//    public List<DiaryResponseDTO> getDateDiaryList(Date findDate){
        List<DiaryEntity> diaryEntityList=diaryRepository.findDateDiaryList(findDate, userIdx);
//        List<DiaryEntity> diaryEntityList=diaryRepository.findDateDiaryList(Integer.parseInt(findDate.substring(0,4)), Integer.parseInt(findDate.substring(5,7)), Integer.parseInt(findDate.substring(8,10)), userIdx);
        return diaryEntityList.stream().map(x->new DiaryResponseDTO(x)).collect(Collectors.toList());
    }

    // get Month diary list
    public List<DiaryResponseDTO> getMonthDiaryList(String findDate, int userIdx){
        List<DiaryEntity> diaryEntityList=diaryRepository.findMonthDiaryList(Integer.parseInt(findDate.substring(0,4)), Integer.parseInt(findDate.substring(5,7)), userIdx);
        return diaryEntityList.stream().map(x->new DiaryResponseDTO(x)).collect(Collectors.toList());
    }

    // get user diary list
    public List<DiaryResponseDTO> userDiaryList(int userIdx){
        List<DiaryEntity> diaryEntityList=diaryRepository.findByUserIdxList(userIdx);
        return diaryEntityList.stream().map(x->new DiaryResponseDTO(x)).collect(Collectors.toList());
    }

    // save diary-group
    public void saveDiaryGroup(GroupDiaryRelationDTO groupDiaryRelationDTO){
        System.out.println("wㅇㄹㅇㄹㄹㄹ");
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
    public int getDiaryCount(int userIdx){
        return diaryRepository.countUserDiary(userIdx);
    }

    // save diary comment
    public DiaryCommentResponseDTO saveComment(DiaryCommentRequestDTO diaryCommentRequestDTO){
        UserEntity user=userRepository.getReferenceById(diaryCommentRequestDTO.getUserIdx());
        DiaryCommentEntity diaryCommentEntity=DiaryCommentEntity.builder().content(diaryCommentRequestDTO.getContent())
                .user(user)
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

    // get diary comment List All
    public List<DiaryCommentResponseDTO> getDiaryCommentListAll(int diaryIdx){
        List<DiaryCommentEntity> diaryCommentEntityList=diaryCommentRepository.findByDiary(DiaryEntity.builder().diaryIdx(diaryIdx).build());
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
    public void saveBookmark(int diaryIdx, int userIdx){
        ScrapEntity scrapEntity=ScrapEntity.builder()
                .user(UserEntity.builder().userIdx(userIdx).build())
                .diary(DiaryEntity.builder().diaryIdx(diaryIdx).build())
                .build();
        bookmarkRepository.save(scrapEntity);
    }

    // get bookmark list
    public List<BookmarkResponseDTO> getBookmarkList(int userIdx){
        List<ScrapEntity> scrapEntityList=bookmarkRepository.findByUser(UserEntity.builder().userIdx(userIdx).build());
        return scrapEntityList.stream().map(x-> new BookmarkResponseDTO(x)).collect(Collectors.toList());
    }

    // delete bookmark
    @Transactional
    public void deleteBookmark(int diaryIdx, int userIdx){
        bookmarkRepository.deleteByDiaryAndUser(DiaryEntity.builder().diaryIdx(diaryIdx).build(), UserEntity.builder().userIdx(userIdx).build());
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