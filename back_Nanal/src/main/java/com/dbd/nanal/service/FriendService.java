package com.dbd.nanal.service;

import com.dbd.nanal.dto.FriendDetailResponseDTO;
import com.dbd.nanal.model.DiaryEntity;
import com.dbd.nanal.model.FriendEntity;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.repository.DiaryRepository;
import com.dbd.nanal.repository.FriendRepository;
import com.dbd.nanal.repository.UserProfileRepository;
import com.dbd.nanal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final DiaryRepository diaryRepository;

    public FriendService(FriendRepository friendRepository, UserRepository userRepository, UserProfileRepository userProfileRepository, DiaryRepository diaryRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public boolean save(HashMap<String, Integer> map) {

        FriendEntity check = friendRepository.isFriendExist(map.get("userIdx"), map.get("friendIdx"));

        if (check != null) {
            // 이미 등록된 친구면 false 리턴
            return false;
        }
        UserEntity user = userRepository.findByUserIdx(map.get("userIdx"));
        UserEntity friend = userRepository.findByUserIdx(map.get("friendIdx"));

        friendRepository.save(FriendEntity.builder().user_idx1(user).user_idx2(friend).build());
        friendRepository.save(FriendEntity.builder().user_idx1(friend).user_idx2(user).build());

        return true;

    }

    // 친구 리스트
    public List<FriendDetailResponseDTO> findFriendList(int userIdx) {

        List<FriendEntity> friends = friendRepository.findAllFriends(userIdx);

        List<FriendDetailResponseDTO> friendDetailResponseDTOS = new ArrayList<>();
        for (FriendEntity friend : friends) {

            friendDetailResponseDTOS.add(new FriendDetailResponseDTO(friend.getUser_idx2().getUserProfile(), friend.getUser_idx2().getUserIdx()));
        }
        return friendDetailResponseDTOS;
    }

    public List<HashMap<String, Object>> findFriendListNotInGroup(int userIdx, int groupIdx) {

        List<FriendEntity> friends = friendRepository.findAllFriendsNotInGroup(userIdx, groupIdx);

        List<HashMap<String, Object>> friendDetailResponseDTOS = new ArrayList<>();
        for (FriendEntity friend : friends) {

            HashMap<String, Object> responseDto = new HashMap<>();
            responseDto.put("userIdx", friend.getUser_idx1().getUserIdx());
            responseDto.put("nickname", friend.getUser_idx1().getUserProfile().getNickname());
            responseDto.put("img", friend.getUser_idx1().getUserProfile().getImg());
            responseDto.put("introduction", friend.getUser_idx1().getUserProfile().getIntroduction());

            friendDetailResponseDTOS.add(responseDto);
        }
        return friendDetailResponseDTOS;

    }

    // 친구 조회
    public FriendDetailResponseDTO findFriend(int userIdx) {

        UserProfileEntity userProfile = userProfileRepository.findFriend(userIdx);
        return new FriendDetailResponseDTO(userProfile, userIdx);

    }

    // 친구 일기 조회
    public List<HashMap<String, Object>> findFriendDiary(int friendIdx) {

        List<HashMap<String, Object>> findFriendDiaryList = new ArrayList<>();
        List<DiaryEntity> diaryEntityList = diaryRepository.findByUserIdxList(friendIdx);

        for (DiaryEntity diary : diaryEntityList) {

            HashMap<String, Object> responseDto = new HashMap<>();
            responseDto.put("diaryIdx", diary.getDiaryIdx());
            responseDto.put("imgUrl", diary.getImgUrl());

            findFriendDiaryList.add(responseDto);
        }
        return findFriendDiaryList;

    }

}
