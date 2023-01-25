package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.config.common.StatusCode;
import com.dbd.nanal.dto.GroupDetailRequestDTO;
import com.dbd.nanal.dto.GroupDetailResponseDTO;
import com.dbd.nanal.dto.GroupUserRelationRequestDTO;
import com.dbd.nanal.dto.GroupUserRelationResponseDTO;
import com.dbd.nanal.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional
    public ResponseEntity<?> save(@ApiParam(value = "그룹 생성 정보") @RequestBody GroupDetailRequestDTO groupDetailRequestDTO) {

        try {
            // 1. group_detail table save, 결과 responseDTO에 저장
            GroupDetailResponseDTO groupDetailResponseDTO = groupService.saveGroup(groupDetailRequestDTO);

            // group_detail table save 성공, 반환
            if (groupDetailResponseDTO != null) {
                // 반환값에서 idx(auto_increment) 가져와서 request의 idx 채우기
                groupDetailRequestDTO.setGroupIdx(groupDetailResponseDTO.getGroupIdx());

                // 2. group_tag table save, 결과 responseDTO에 저장
                groupDetailResponseDTO.setTags(groupService.saveGroupTags(groupDetailRequestDTO).getTags());

                // 저장 성공
                if (groupDetailResponseDTO.getTags() != null) {
                    // FRONT - responseDTO(그룹 상세 정보) 전달
                    HashMap<String, Object> responseDTO = new HashMap<>();
                    responseDTO.put("groupDetail", groupDetailResponseDTO);
                    return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.GROUP_FIND_SUCCESS, responseDTO), HttpStatus.OK);
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
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.NO_CONTENT);
        }

    }

    @ApiOperation(value = "그룹 상세 정보 조회", notes =
            "groupIdx 값으로 그룹 상세 정보를 조회합니다.\n" +
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
                // 결과 넣기!!
                HashMap<String, Object> responseDTO = new HashMap<>();
                responseDTO.put("groupDetail", groupDetailResponseDTO);

                return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.GROUP_FIND_SUCCESS, responseDTO), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.GROUP_FIND_FAIL), HttpStatus.NO_CONTENT);
            }
        }
        // Exception 발생
        catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.NO_CONTENT);
        }
    }


    @ApiOperation(value = "그룹 가입", notes =
            "(초대 수락) userIdx 사용자가 groupIdx 그룹에 가입합니다.\n" +
                    "[Front] \n" +
                    "JSON\n" +
                    "{groupIdx(int), userIdx(int)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{success(boolean)}")

    @PostMapping("/join")
    public ResponseEntity<?> groupJoin(@ApiParam(value = "groupIdx, userIdx", required = true) @RequestBody Map<String, Integer> requestDTO) {
        try {
            GroupUserRelationRequestDTO groupUserRelationRequestDTO = new GroupUserRelationRequestDTO(requestDTO.get("userIdx"), requestDTO.get("groupIdx"));
            System.out.println(groupUserRelationRequestDTO.getUserIdx() + " groupIdx : " + groupUserRelationRequestDTO.getGroupIdx());

            GroupUserRelationResponseDTO groupUserRelationResponseDTO = groupService.saveGroupUserRelation(groupUserRelationRequestDTO);

            if (groupUserRelationResponseDTO != null) {
                // 반환값 고민중 !
                HashMap<String, Object> responseDTO = new HashMap<>();
                responseDTO.put("RESULT", null);

                return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.GROUP_FIND_SUCCESS, responseDTO), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.GROUP_FIND_FAIL), HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation(value = "그룹 리스트 조회", notes =
            "userIdx 사용자가 소속된 그룹 리스트를 조회합니다.\n" +
                    "[Front] \n" +
                    "JSON\n" +
                    "{userIdx(int)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{List<GroupDetailResponse>}")
    @GetMapping("/list/{userIdx}")
    public ResponseEntity<?> getGroupList(@ApiParam(value = "유저 idx", required = true) @PathVariable int userIdx) {
        try {

            // group_user_relation 테이블에서 userIdx가 포함된 group찾기
            List<GroupDetailResponseDTO> groupDetailResponseDTOS =
                    groupService.getGroupList(userIdx);

            if (groupDetailResponseDTOS != null) {
                HashMap<String, Object> responseDTO = new HashMap<>();
                responseDTO.put("groupList", groupDetailResponseDTOS);

                return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.GROUP_FIND_SUCCESS, responseDTO), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.GROUP_FIND_FAIL), HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.NO_CONTENT);
        }


    }


}
