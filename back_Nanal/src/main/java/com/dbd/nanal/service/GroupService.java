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
    public GroupTagResponseDTO saveGroupTags(GroupDetailRequestDTO groupDetailRequestDTO) {
        List<String> groupTagRequestDTOs = groupDetailRequestDTO.getTags();

        List<GroupTagEntity> groupTagEntities = new ArrayList<>();

        for (String tag : groupTagRequestDTOs) {
            GroupTagRequestDTO groupTagRequestDTO = new GroupTagRequestDTO();
            groupTagRequestDTO.setTag(tag);
            groupTagRequestDTO.setGroupDetail(groupDetailRequestDTO.toEntity());

            groupTagEntities.add(groupTagRepository.save(groupTagRequestDTO.toEntity()));
        }
//        return null;
        return new GroupTagResponseDTO(groupTagEntities);
    }

    // get Group
    public GroupDetailResponseDTO findGroupById(int groupIdx) {

        GroupDetailEntity groupEntity = groupRepository.getReferenceById(groupIdx);

        return new GroupDetailResponseDTO(groupEntity);
    }

    // get Group List
    public List<GroupDetailResponseDTO> getGroupList(int userIdx) {

        List<GroupDetailEntity> groupUserRelationEntities = groupUserRelationRepository.findGroupList(userIdx);
        System.out.println("size : " + groupUserRelationEntities.size());

        return groupUserRelationEntities.stream().map(GroupDetailResponseDTO::new).collect(Collectors.toList());

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

    public boolean deleteGroupUser(int userIdx, int groupIdx) {

        GroupUserRelationEntity groupUserRelationEntity = groupUserRelationRepository.findByUserIdGroupID(userIdx, groupIdx);
        System.out.println(groupUserRelationEntity.getGroupUserIdx());
        groupUserRelationRepository.delete(groupUserRelationEntity);

        return false;
    }
}
