package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
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
        HashMap<String, Object> responseDTO = new HashMap<>();

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
                    responseDTO.put("responseMessage", ResponseMessage.GROUP_SAVE_SUCCESS);
                    responseDTO.put("groupDetail", groupDetailResponseDTO);
                    return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
                }
                // 반환 실패
                else {
                    responseDTO.put("responseMessage", ResponseMessage.GROUP_SAVE_FAIL);
                    return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
                }
            }
            // 반환 실패
            else {
                responseDTO.put("responseMessage", ResponseMessage.GROUP_SAVE_FAIL);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
            }

        }
        // Exception 발생
        catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.OK);
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
        HashMap<String, Object> responseDTO = new HashMap<>();
        try {
            GroupDetailResponseDTO groupDetailResponseDTO = groupService.findGroupById(groupIdx);
            // groupIdx 이용해서 group_detail table에서 정보 찾기

            // 반환 성공
            if (groupDetailResponseDTO != null) {
                // FRONT로 responseDTO(그룹 상세 정보) 전달
                // 결과 넣기!!
                responseDTO.put("groupDetail", groupDetailResponseDTO);
                responseDTO.put("responseMessage", ResponseMessage.GROUP_FIND_SUCCESS);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            } else {
                responseDTO.put("responseMessage", ResponseMessage.GROUP_FIND_FAIL);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
            }
        }
        // Exception 발생
        catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
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

        HashMap<String, Object> responseDTO = new HashMap<>();
        try {
            GroupUserRelationRequestDTO groupUserRelationRequestDTO = new GroupUserRelationRequestDTO(requestDTO.get("userIdx"), requestDTO.get("groupIdx"));
            GroupUserRelationResponseDTO groupUserRelationResponseDTO = groupService.saveGroupUserRelation(groupUserRelationRequestDTO);

            if (groupUserRelationResponseDTO != null) {
                // 반환값 고민중 !
                responseDTO.put("responseMessage", ResponseMessage.GROUP_JOIN_SUCCESS);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            } else {
                responseDTO.put("responseMessage", ResponseMessage.GROUP_JOIN_FAIL);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.INTERNAL_SERVER_ERROR);
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
        HashMap<String, Object> responseDTO = new HashMap<>();

        try {
            // group_user_relation 테이블에서 userIdx가 포함된 group찾기
            List<GroupDetailResponseDTO> groupDetailResponseDTOS =
                    groupService.getGroupList(userIdx);

            if (groupDetailResponseDTOS != null) {
                responseDTO.put("groupList", groupDetailResponseDTOS);
                responseDTO.put("responseMessage", ResponseMessage.GROUP_LIST_FIND_SUCCESS);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            } else {
                responseDTO.put("responseMessage", ResponseMessage.GROUP_LIST_FIND_FAIL);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
            }

        } catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }


    }


    @ApiOperation(value = "그룹 탈퇴하기", notes =
            "userIdx 사용자가 groupIdx 그룹을 탈퇴합니다.\n" +
                    "[Front] \n" +
                    "JSON\n" +
                    "{userIdx(int), groupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{}")
    @DeleteMapping("/{userIdx}/{groupIdx}")
    public ResponseEntity<?> withDrawGroup(@ApiParam(value = "유저 idx", required = true) @PathVariable int userIdx, @ApiParam(value = "유저 idx", required = true) @PathVariable int groupIdx) {
        HashMap<String, Object> responseDTO = new HashMap<>();

        groupService.deleteGroupUser(userIdx, groupIdx);
        responseDTO.put("responseMessage", ResponseMessage.GROUP_USER_DELETE_SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }
}
