package com.dbd.nanal.service;

import com.dbd.nanal.dto.*;
import com.dbd.nanal.model.GroupDetailEntity;
import com.dbd.nanal.model.GroupTagEntity;
import com.dbd.nanal.model.GroupUserRelationEntity;
import com.dbd.nanal.repository.GroupRepository;
import com.dbd.nanal.repository.GroupTagRepository;
import com.dbd.nanal.repository.GroupUserRelationRepository;
import com.dbd.nanal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupService {

    //    @Autowired
    private GroupRepository groupRepository;
    private GroupTagRepository groupTagRepository;
    private GroupUserRelationRepository groupUserRelationRepository;
    private UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, GroupTagRepository groupTagRepository, UserRepository userRepository, GroupUserRelationRepository groupUserRelationRepository) {
        this.groupRepository = groupRepository;
        this.groupTagRepository = groupTagRepository;
        this.userRepository = userRepository;
        this.groupUserRelationRepository = groupUserRelationRepository;
    }

    // save Group
    public GroupDetailResponseDTO saveGroup(GroupDetailRequestDTO groupDetailRequestDTO) {
        return new GroupDetailResponseDTO(groupRepository.save(groupDetailRequestDTO.toEntity()));
    }

    // save Group Tags
    @Transactional
    public List<GroupTagResponseDTO> saveGroupTags(GroupDetailRequestDTO groupDetailRequestDTO) {
//    public GroupTagResponseDTO saveGroupTags(GroupDetailRequestDTO groupDetailRequestDTO) {
        List<String> groupTagRequestDTOs = groupDetailRequestDTO.getTags();
        List<GroupTagResponseDTO> groupTagResponseDTOS = new ArrayList<>();

        for (String tag : groupTagRequestDTOs) {
            // 저장할 태그 DTO
            GroupTagRequestDTO groupTagRequestDTO = new GroupTagRequestDTO();

            // 태그 정보 세팅
            groupTagRequestDTO.setTag(tag);
            groupTagRequestDTO.setGroupDetail(groupDetailRequestDTO.toEntity());

            // 리스트에 추가
            groupTagResponseDTOS.add(new GroupTagResponseDTO(groupTagRepository.save(groupTagRequestDTO.toEntity())));
        }
        return groupTagResponseDTOS;
    }

    // get Group
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

    // get Group List
    public List<HashMap<String, Object>> getGroupList(int userIdx) {

        List<HashMap<String, Object>> result = new ArrayList<>();

        List<GroupDetailEntity> groupDetailEntities = groupUserRelationRepository.findGroupList(userIdx);

        for(GroupDetailEntity groupDetailEntity : groupDetailEntities){
            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("groupDetail", new GroupDetailResponseDTO(groupDetailEntity));

            List<GroupTagResponseDTO> tags = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                tags.add(new GroupTagResponseDTO(groupDetailEntity.getGroupTags().get(i)));
            }
            responseDTO.put("tags", tags);
            result.add(responseDTO);
        }
//        System.out.println("size : " + groupUserRelationEntities.size());

        return result;
//        return groupUserRelationEntities.stream().map(GroupDetailResponseDTO::new).collect(Collectors.toList());
    }

    // save group - user relation (그룹 생성 시 유저 초대 -> 수락 시 발생)
    @Transactional
    public GroupUserRelationResponseDTO saveGroupUserRelation(GroupUserRelationRequestDTO groupUserRelationRequestDTO) {

        // GroupUserRelationRequestDTO의 userEntity 채우기
        groupUserRelationRequestDTO.setUserEntity(userRepository.getReferenceById(groupUserRelationRequestDTO.getUserIdx()));

        // GroupUserRelationRequestDTO의 groupDetail 채우기
        groupUserRelationRequestDTO.setGroupDetail(groupRepository.getReferenceById(groupUserRelationRequestDTO.getGroupIdx()));

        // 엔티티로 변환해서 저장
        GroupUserRelationEntity groupUserRelationEntity = groupUserRelationRepository.save(groupUserRelationRequestDTO.toEntity());
        System.out.println(groupUserRelationEntity.getGroupUserIdx());

        return new GroupUserRelationResponseDTO(groupUserRelationEntity);
    }

    // update group detail
    @Transactional
    public  HashMap<String, Object>  updateGroupDetail(GroupDetailRequestDTO groupDetailRequestDTO) {

        // 수정 대상 엔티티 가져오기
        GroupDetailEntity groupDetailEntity = groupRepository.getReferenceById(groupDetailRequestDTO.getGroupIdx());

        // group detail - 이름, 이미지 수정
        groupDetailEntity.setGroupName(groupDetailRequestDTO.getGroupName());
        groupDetailEntity.setGroupImg(groupDetailRequestDTO.getGroupImg());

        // group tag 수정
        List<GroupTagEntity> groupTagEntities = groupDetailEntity.getGroupTags();

        // 리턴
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

    public boolean deleteGroupUser(int userIdx, int groupIdx) {

        GroupUserRelationEntity groupUserRelationEntity = groupUserRelationRepository.findByUserIdGroupID(userIdx, groupIdx);
        System.out.println(groupUserRelationEntity.getGroupUserIdx());
        groupUserRelationRepository.delete(groupUserRelationEntity);

        return false;
    }
}
