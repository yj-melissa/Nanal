package com.dbd.nanal.service;

import com.dbd.nanal.dto.SearchDiaryResponseDTO;
import com.dbd.nanal.dto.SearchUserIdResponseDTO;
import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.repository.DiaryRepository;
import com.dbd.nanal.repository.GroupRepository;
import com.dbd.nanal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
//    private final GroupRepository groupRepository;

    // get user id list
    public List<SearchUserIdResponseDTO> getUser(String id, int userIdx){
        List<UserEntity> userEntityList=userRepository.searchUserId(id, userIdx);
        return userEntityList.stream().map(x-> new SearchUserIdResponseDTO(x)).collect(Collectors.toList());
    }

    // get diary
    public List<SearchDiaryResponseDTO> getDiaryContent(String content, int userIdx){
        List<DiaryEntity> diaryEntityList=diaryRepository.searchDiary(content, userIdx);
        return diaryEntityList.stream().map(x-> new SearchDiaryResponseDTO(x)).collect(Collectors.toList());
    }
}
