package com.dbd.nanal.service;

import com.dbd.nanal.dto.GroupDetailRequestDTO;
import com.dbd.nanal.dto.GroupDetailResponseDTO;
import com.dbd.nanal.dto.GroupTagRequestDTO;
import com.dbd.nanal.dto.GroupTagResponseDTO;
import com.dbd.nanal.model.GroupDetailEntity;
import com.dbd.nanal.model.GroupTagEntity;
import com.dbd.nanal.repository.GroupRepository;
import com.dbd.nanal.repository.GroupTagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class GroupService {

    //    @Autowired
    private GroupRepository groupRepository;
    private GroupTagRepository groupTagRepository;

    public GroupService(GroupRepository groupRepository, GroupTagRepository groupTagRepository) {
        this.groupRepository = groupRepository;
        this.groupTagRepository = groupTagRepository;
    }

    public GroupDetailResponseDTO saveGroup(GroupDetailRequestDTO groupDetailRequestDTO) {
        return new GroupDetailResponseDTO(groupRepository.save(groupDetailRequestDTO.toEntity()));
    }
    public GroupTagResponseDTO saveGroupTags(GroupDetailRequestDTO groupDetailRequestDTO){
        List<String> groupTagRequestDTOs = groupDetailRequestDTO.getTags();

        List<GroupTagEntity> groupTagEntities = new ArrayList<>();

        for(String tag : groupTagRequestDTOs){
            GroupTagRequestDTO groupTagRequestDTO = new GroupTagRequestDTO();
            groupTagRequestDTO.setTag(tag);
            groupTagRequestDTO.setGroupDetail(groupDetailRequestDTO.toEntity());

            groupTagEntities.add(groupTagRepository.save(groupTagRequestDTO.toEntity()));
        }
//        return null;
        return new GroupTagResponseDTO(groupTagEntities);
    }

    public GroupDetailResponseDTO findGroupById(int groupIdx) {

        GroupDetailEntity groupEntity = groupRepository.getById(groupIdx);

        return new GroupDetailResponseDTO(groupEntity);
    }

}
