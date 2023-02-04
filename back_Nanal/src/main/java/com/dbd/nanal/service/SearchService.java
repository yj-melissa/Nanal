package com.dbd.nanal.service;

import com.dbd.nanal.dto.SearchDiaryResponseDTO;
import com.dbd.nanal.dto.SearchUserIdResponseDTO;
import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.model.GroupDiaryRelationEntity;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.repository.DiaryRepository;
import com.dbd.nanal.repository.GroupDiaryRelationRepository;
import com.dbd.nanal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final GroupDiaryRelationRepository groupDiaryRelationRepository;

    // get user id list
    public List<SearchUserIdResponseDTO> getUser(String id, int userIdx){
        List<UserEntity> userEntityList=userRepository.searchUserId(id, userIdx);
        return userEntityList.stream().map(x-> new SearchUserIdResponseDTO(x)).collect(Collectors.toList());
    }

    // get diary
    public List<SearchDiaryResponseDTO> getDiaryContent(String content, int userIdx){
        List<DiaryEntity> diaryEntityList=diaryRepository.searchDiary(content, userIdx);
        List<SearchDiaryResponseDTO> searchDiaryResponseDTOList=new ArrayList<>();
        for(DiaryEntity diaryEntity: diaryEntityList){
            List<GroupDiaryRelationEntity> groupDiaryRelationEntityList=groupDiaryRelationRepository.findByDiary(diaryEntity);
            for(GroupDiaryRelationEntity groupDiaryRelationEntity: groupDiaryRelationEntityList){
                SearchDiaryResponseDTO searchDiaryResponseDTO=new SearchDiaryResponseDTO(diaryEntity);
                searchDiaryResponseDTO.setGroupIdx(groupDiaryRelationEntity.getGroupDetail().getGroupIdx());
                searchDiaryResponseDTO.setGroupName(groupDiaryRelationEntity.getGroupDetail().getGroupName());
                searchDiaryResponseDTOList.add(searchDiaryResponseDTO);
            }
        }
        return searchDiaryResponseDTOList;
    }
}
