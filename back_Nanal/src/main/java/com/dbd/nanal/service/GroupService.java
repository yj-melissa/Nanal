package com.dbd.nanal.service;

import com.dbd.nanal.dto.GroupResponseDTO;
import com.dbd.nanal.model.GroupMemberEntity;
import com.dbd.nanal.model.GroupTagEntity;
import com.dbd.nanal.repository.GroupRepository;
import com.dbd.nanal.repository.GroupTagRepository;
import org.springframework.stereotype.Service;


@Service
public class GroupService {

    //    @Autowired
    private GroupRepository groupRepository;
    private GroupTagRepository groupTagRepository;

    public GroupService(GroupRepository groupRepository, GroupTagRepository groupTagRepository) {
        this.groupRepository = groupRepository;
        this.groupTagRepository = groupTagRepository;
    }

    public GroupMemberEntity saveGroup(GroupMemberEntity groupEntity) {
        System.out.println("서비스ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        groupRepository.save(groupEntity);

        return groupEntity;
    }
    public GroupTagEntity saveGroupTag(GroupTagEntity groupTagEntity){
        groupTagRepository.save(groupTagEntity);
        return groupTagEntity;
    }

    public GroupResponseDTO findGroupById(int groupIdx) {

        GroupMemberEntity groupEntity = groupRepository.getById(groupIdx);

        return new GroupResponseDTO(groupEntity);

    }

}
