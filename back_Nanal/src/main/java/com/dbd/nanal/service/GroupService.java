package com.dbd.nanal.service;

import com.dbd.nanal.dto.*;
import com.dbd.nanal.model.GroupDetailEntity;
import com.dbd.nanal.model.GroupTagEntity;
import com.dbd.nanal.model.GroupUserRelationEntity;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.repository.GroupRepository;
import com.dbd.nanal.repository.GroupTagRepository;
import com.dbd.nanal.repository.GroupUserRelationRepository;
import com.dbd.nanal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupTagRepository groupTagRepository;
    private final GroupUserRelationRepository groupUserRelationRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, GroupTagRepository groupTagRepository, UserRepository userRepository, GroupUserRelationRepository groupUserRelationRepository) {
        this.groupRepository = groupRepository;
        this.groupTagRepository = groupTagRepository;
        this.userRepository = userRepository;
        this.groupUserRelationRepository = groupUserRelationRepository;

    }

    public GroupDetailResponseDTO saveGroup(GroupDetailRequestDTO groupDetailRequestDTO) {

        return new GroupDetailResponseDTO(groupRepository.save(groupDetailRequestDTO.toEntity()));

    }

    @Transactional
    public List<GroupTagResponseDTO> saveGroupTags(GroupDetailRequestDTO groupDetailRequestDTO) {

        List<String> groupTagRequestDTOs = groupDetailRequestDTO.getTags();
        List<GroupTagResponseDTO> groupTagResponseDTOS = new ArrayList<>();

        for (String tag : groupTagRequestDTOs) {
            GroupTagRequestDTO groupTagRequestDTO = new GroupTagRequestDTO();

            groupTagRequestDTO.setTag(tag);
            groupTagRequestDTO.setGroupDetail(groupDetailRequestDTO.toEntity());

            groupTagResponseDTOS.add(new GroupTagResponseDTO(groupTagRepository.save(groupTagRequestDTO.toEntity())));
        }
        return groupTagResponseDTOS;

    }

    public HashMap<String, Object> findGroupById(int groupIdx) {

        GroupDetailEntity groupEntity = groupRepository.getReferenceById(groupIdx);
        HashMap<String, Object> responseDTO = new HashMap<>();

        List<GroupTagResponseDTO> tags = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tags.add(new GroupTagResponseDTO(groupEntity.getGroupTags().get(i)));
        }
        responseDTO.put("tags", tags);
        responseDTO.put("groupDetail", new GroupDetailResponseDTO(groupEntity));
        return responseDTO;

    }

    public List<HashMap<String, Object>> getGroupList(int userIdx, int opt) {

        List<HashMap<String, Object>> result = new ArrayList<>();

        if (opt == 0) { // 이름순 정렬
            List<GroupDetailEntity> groupDetailEntities = groupUserRelationRepository.findGroupListByName(userIdx);

            for (GroupDetailEntity groupDetailEntity : groupDetailEntities) {
                HashMap<String, Object> responseDTO = new HashMap<>();
                responseDTO.put("groupDetail", new GroupDetailResponseDTO(groupDetailEntity));

                List<GroupTagResponseDTO> tags = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    tags.add(new GroupTagResponseDTO(groupDetailEntity.getGroupTags().get(i)));
                }
                responseDTO.put("tags", tags);
                result.add(responseDTO);
            }

        } else { // 최신일기 작성 시간순 정렬
            List<Object[]> groupList = groupUserRelationRepository.findGroupListByTime(userIdx);
            int size = groupList.size() / 5;

            int idx = 0;
            for (int i = 0; i < size; i++) {
                // group 정보
                HashMap<String, Object> responseDTO = new HashMap<>();

                Object[] groupListNativeDTO = groupList.get(idx++);

                HashMap<String, Object> groupDetail = new HashMap<>();

                groupDetail.put("groupIdx", groupListNativeDTO[0]);
                groupDetail.put("imgUrl", groupListNativeDTO[1]);
                groupDetail.put("groupName", groupListNativeDTO[2]);

                List<HashMap<String, Object>> tags = new ArrayList<>();
                HashMap<String, Object> tag = new HashMap<>();
                tag.put("tagIdx", groupListNativeDTO[3]);
                tag.put("tag", groupListNativeDTO[4]);
                tags.add(tag);

                for (int j = 1; j < 5; j++) {
                    groupListNativeDTO = groupList.get(idx++);
                    tag = new HashMap<>();
                    tag.put("tagIdx", groupListNativeDTO[3]);
                    tag.put("tag", groupListNativeDTO[4]);
                    tags.add(tag);
                }
                responseDTO.put("groupDetail", groupDetail);
                responseDTO.put("tags", tags);
                result.add(responseDTO);
            }
        }
        return result;

    }

    // save group - user relation (그룹 생성 시 유저 초대 -> 수락 시 발생)
    @Transactional
    public GroupUserRelationResponseDTO saveGroupUserRelation(GroupUserRelationRequestDTO groupUserRelationRequestDTO) {

        // 이미 가입했는지 확인하기
        GroupUserRelationEntity groupUserRelationEntity = groupUserRelationRepository.findByUserIdGroupID(groupUserRelationRequestDTO.getUserIdx(), groupUserRelationRequestDTO.getGroupIdx());

        if (groupUserRelationEntity == null) {

            // GroupUserRelationRequestDTO의 userEntity 채우기
            groupUserRelationRequestDTO.setUserEntity(userRepository.getReferenceById(groupUserRelationRequestDTO.getUserIdx()));

            // GroupUserRelationRequestDTO의 groupDetail 채우기
            groupUserRelationRequestDTO.setGroupDetail(groupRepository.getReferenceById(groupUserRelationRequestDTO.getGroupIdx()));

            // 엔티티로 변환해서 저장
            groupUserRelationEntity = groupUserRelationRepository.save(groupUserRelationRequestDTO.toEntity());

            return new GroupUserRelationResponseDTO(groupUserRelationEntity);
        }

        return null;

    }

    @Transactional
    public HashMap<String, Object> updateGroupDetail(GroupDetailRequestDTO groupDetailRequestDTO) {

        // 수정 대상 엔티티 가져오기
        GroupDetailEntity groupDetailEntity = groupRepository.getReferenceById(groupDetailRequestDTO.getGroupIdx());

        // group detail - 이름, 이미지 수정
        groupDetailEntity.setGroupName(groupDetailRequestDTO.getGroupName());
        groupDetailEntity.setGroupImgIdx(groupDetailRequestDTO.getGroupImgIdx());

        // group tag 수정
        List<GroupTagEntity> groupTagEntities = groupDetailEntity.getGroupTags();

        List<GroupTagResponseDTO> tags = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            // DB 반영
            groupTagEntities.get(i).setTag(groupDetailRequestDTO.getTags().get(i));
            // 리턴
            tags.add(new GroupTagResponseDTO(groupTagEntities.get(i)));
        }

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("tags", tags);
        responseDTO.put("groupDetail", new GroupDetailResponseDTO(groupDetailEntity));

        return responseDTO;

    }

    public void deleteGroupUser(int userIdx, int groupIdx) {

        GroupUserRelationEntity groupUserRelationEntity = groupUserRelationRepository.findByUserIdGroupID(userIdx, groupIdx);
        groupUserRelationRepository.delete(groupUserRelationEntity);

    }

    public List<HashMap<String, Object>> findGroupUser(int userIdx, int groupIdx) {

        List<UserEntity> groupUsers = groupUserRelationRepository.findGroupUser(userIdx, groupIdx);

        List<HashMap<String, Object>> friendDetailResponseDTOS = new ArrayList<>();
        for (UserEntity user : groupUsers) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("userIdx", user.getUserIdx());
            map.put("nickname", user.getUserProfile().getNickname());
            map.put("img", user.getUserProfile().getImg());
            map.put("introduction", user.getUserProfile().getIntroduction());
            friendDetailResponseDTOS.add(map);
        }

        return friendDetailResponseDTOS;

    }

    @Transactional
    public void updateGroupImg(int groupIdx, int groupImgIdx, String imgUrl) {

        GroupDetailEntity groupDetailEntity = groupRepository.getReferenceById(groupIdx);
        // 리턴받은 이미지 인덱스로 업데이트
        groupDetailEntity.setImgUrl(imgUrl);
        groupDetailEntity.setGroupImgIdx(groupImgIdx);

    }

    public void deleteGroup(int groupIdx) {
        groupRepository.deleteById(groupIdx);
    }
}
