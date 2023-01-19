package com.dbd.nanal.controller;

import com.dbd.nanal.dto.GroupRequestDTO;
import com.dbd.nanal.dto.GroupResponseDTO;
import com.dbd.nanal.model.GroupMemberEntity;
import com.dbd.nanal.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// Api tag는 제목 역할을 한다.
@Api(tags = {"Group"})
@RestController
@RequiredArgsConstructor
@RequestMapping("group")
public class GroupController {

    private final GroupService groupService;

    // @ApiOperation value : 리스트로 봤을 때 간단하게 보이는 용도
    // @ApiOperation notes : 펼쳐봤을 때 자세히 알려주는 용도
    @ApiOperation(value = "그룹 생성", notes = "새로운 그룹을 생성합니다.")
    @PostMapping
    public GroupMemberEntity save(@ApiParam(value = "그룹 정보") @RequestBody GroupRequestDTO groupRequestDTO) {
        return groupService.save(groupRequestDTO.toEntity());
    }


    @ApiOperation(value = "그룹 조회", notes = "그룹 정보를 조회합니다.")
    @GetMapping("/{groupId}")
    // 그룹 상세정보 리턴
    public Optional<GroupResponseDTO> getGroupDTO(@ApiParam(value = "그룹 id", required = true) @PathVariable int groupId) {
        GroupResponseDTO groupDTO = groupService.findGroupById(groupId);

        return Optional.of(groupDTO);
    }


}
