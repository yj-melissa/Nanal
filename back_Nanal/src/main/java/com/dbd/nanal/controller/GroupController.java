package com.dbd.nanal.controller;

import com.dbd.nanal.dto.GroupRequestDTO;
import com.dbd.nanal.dto.GroupResponseDTO;
import com.dbd.nanal.dto.GroupTagRequestDTO;
import com.dbd.nanal.model.GroupMemberEntity;
import com.dbd.nanal.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ApiOperation(value = "그룹 생성", notes = "새로운 그룹을 생성합니다.\n"+"front \n" +
            "보내줄 정보의\n" +
            "이름 : 의미, 약속\n" +
            "\n" +
            "back\n" +
            "{\n" +
            "  \"creationTime\": \"2023-01-20T04:20:32.473Z\",\n" +
            "  \"groupIdx\": 0, (식별자 : int)\n" +
            "  \"groupImg\": \"string\",\n" +
            "  \"groupName\": \"string\",\n" +
            "  \"private\": true\n" +
            "}")
    @PostMapping
    public ResponseEntity<GroupMemberEntity> save(@ApiParam(value = "그룹 정보") @RequestBody GroupRequestDTO groupRequestDTO) {
//    public ResponseEntity<GroupMemberEntity> save(@ApiParam(value = "그룹 정보") @RequestBody GroupRequestDTO groupRequestDTO, @ApiParam(value = "태그 정보") @RequestBody GroupTagRequestDTO groupTagRequestDTO) {
//        groupService.save()
        System.out.println("post!");
        // DTO로 받기
//        GroupResponseDTO groupResponseDTO = new GroupResponseDTO(groupService.saveGroup(groupRequestDTO.toEntity()));
        // 엔티티로 받기
        GroupMemberEntity groupMemberEntity = groupService.saveGroup(groupRequestDTO.toEntity());
        // 이름 출력
        System.out.println(groupMemberEntity.getGroupName());

//        groupService.saveGroupTag(groupTagRequestDTO.toEntity(groupMemberEntity));
        return new ResponseEntity<GroupMemberEntity>(HttpStatus.OK);
    }

    @PostMapping("/test")
//    public ResponseEntity<GroupMemberEntity> save(@ApiParam(value = "그룹 정보") @RequestBody GroupRequestDTO groupRequestDTO) {
    public ResponseEntity<GroupMemberEntity> save(@ApiParam(value = "그룹 정보") {//@RequestBody GroupRequestDTO groupRequestDTO) {
//        groupService.save()
        System.out.println("post test!");
        // DTO로 받기
//        GroupResponseDTO groupResponseDTO = new GroupResponseDTO(groupService.saveGroup(groupRequestDTO.toEntity()));
        // 엔티티로 받기
//        GroupMemberEntity groupMemberEntity = groupService.saveGroup(groupRequestDTO.toEntity());
//         이름 출력
//        System.out.println(groupMemberEntity.getGroupName());

//        groupService.saveGroupTag(groupTagRequestDTO.toEntity(groupMemberEntity));
        return new ResponseEntity<GroupMemberEntity>(HttpStatus.OK);
    }

    @ApiOperation(value = "그룹 조회", notes = "그룹 정보를 조회합니다.")
    @GetMapping("/{groupId}")
    // 그룹 상세정보 리턴
    public Optional<GroupResponseDTO> getGroupDTO(@ApiParam(value = "그룹 id", required = true) @PathVariable int groupId) {
        GroupResponseDTO groupDTO = groupService.findGroupById(groupId);

        return Optional.of(groupDTO);
    }

//    @PostMapping()

}
