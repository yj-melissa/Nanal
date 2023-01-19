package com.dbd.nanal.service;

import com.dbd.nanal.dto.GroupResponseDTO;
import com.dbd.nanal.model.GroupMemberEntity;
import com.dbd.nanal.repository.GroupRepository;
import org.springframework.stereotype.Service;


@Service
public class GroupService {

    //    @Autowired
    private GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupMemberEntity save(GroupMemberEntity groupEntity) {

        groupRepository.save(groupEntity);
        return groupEntity;
    }

    public GroupResponseDTO findGroupById(int groupId) {

        GroupMemberEntity groupEntity = groupRepository.getById(groupId);

        return new GroupResponseDTO(groupEntity);

    }

}
