package com.dbd.nanal.controller;

import com.dbd.nanal.dto.GroupDetailRequestDTO;
import com.dbd.nanal.dto.GroupDetailResponseDTO;
import com.dbd.nanal.dto.GroupTagResponseDTO;
import com.dbd.nanal.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Api tag는 제목 역할을 한다.
@Api(tags = {"Group관련 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("group")
public class GroupController {

    private final GroupService groupService;

    @ApiOperation(value = "새로운 그룹 생성", notes =
            "새로운 그룹을 생성합니다.\n" +
                    "[Front] \n" +
                    "JSON\n" +
                    "{groupIdx(int), groupImg(String), groupName(String), private(boolean), tags(List(String)), creationDate(String)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{groupIdx(int), groupImg(String), groupName(String), private(boolean), tags(List(String)), creationDate(String)} ")
    @PostMapping
    public ResponseEntity<?> save(@ApiParam(value = "그룹 생성 정보") @RequestBody GroupDetailRequestDTO groupDetailRequestDTO) {

        try {
            // 1. group_detail table insert, 결과 responseDTO에 저장
            GroupDetailResponseDTO groupDetailResponseDTO = groupService.saveGroup(groupDetailRequestDTO);

            // 반환 성공
            if (groupDetailResponseDTO != null) {
                // 반환값 이용해서 idx 저장
                groupDetailRequestDTO.setGroupIdx(groupDetailResponseDTO.getGroupIdx());

                // 2. group_tag table insert
                GroupTagResponseDTO groupTagResponseDTO = groupService.saveGroupTags(groupDetailRequestDTO);

                // 반환 성공
                if (groupTagResponseDTO != null) {
                    // 반환할 responseDTO에 tag리스트 저장
                    groupDetailResponseDTO.setTags(groupTagResponseDTO.getTags());

                    // FRONT로 responseDTO(그룹 상세 정보) 전달
                    return new ResponseEntity<>(groupDetailResponseDTO, HttpStatus.OK);
                }
                // 반환 실패
                else {
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                }
            }
            // 반환 실패
            else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }

        }
        // Exception 발생
        catch (Exception e) {
            return new ResponseEntity<>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(value = "groupIdx로 그룹 상세 정보 조회", notes =
            "그룹 상세 정보를 조회합니다.\n" +
                    "[Front] \n" +
                    "JSON\n" +
                    "{groupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{groupIdx(int), groupImg(String), groupName(String), private(boolean), tags(List(String)), creationDate(String)} ")
    @GetMapping("/{groupIdx}")
    public ResponseEntity<?> getGroupDTO(@ApiParam(value = "그룹 id", required = true) @PathVariable int groupIdx) {
        try {
            // groupIdx 이용해서 group_detail table에서 정보 찾기
            GroupDetailResponseDTO groupDetailResponseDTO = groupService.findGroupById(groupIdx);

            // 반환 성공
            if (groupDetailResponseDTO != null) {
                // FRONT로 responseDTO(그룹 상세 정보) 전달
                return new ResponseEntity<>(groupDetailResponseDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
        }
        // Exception 발생
        catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
