package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.*;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"Group관련 API"})
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("group")
public class GroupController {

    private final GroupService groupService;

    @ApiOperation(value = "새로운 그룹 생성", notes =
            "새로운 그룹을 생성합니다.\n" +
                    "[Front] \n" +
                    "{groupIdx(int), groupImg(String), groupName(String), private(boolean), tags(List(String)), creationDate(String)} \n\n" +
                    "[Back] \n" +
                    "{groupIdx(int), groupImg(String), groupName(String), private(boolean), tags(List(String)), creationDate(String)} ")

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@ApiParam(value = "그룹 생성 정보") @RequestBody GroupDetailRequestDTO groupDetailRequestDTO, @AuthenticationPrincipal UserEntity userInfo) {

        HashMap<String, Object> responseDTO = new HashMap<>();

        try {
//            groupDetailRequestDTO.
            GroupDetailResponseDTO groupDetailResponseDTO = groupService.saveGroup(groupDetailRequestDTO);

            if (groupDetailResponseDTO != null) {
                groupDetailRequestDTO.setGroupIdx(groupDetailResponseDTO.getGroupIdx());

                // 사용자부터 그룹에 가입
                GroupUserRelationRequestDTO groupUserRelationRequestDTO = new GroupUserRelationRequestDTO(userInfo.getUserIdx(), groupDetailRequestDTO.getGroupIdx());
                groupService.saveGroupUserRelation(groupUserRelationRequestDTO);

                List<GroupTagResponseDTO> groupTagResponseDTOS = groupService.saveGroupTags(groupDetailRequestDTO);
                if (groupTagResponseDTOS != null) {
                    responseDTO.put("responseMessage", ResponseMessage.GROUP_SAVE_SUCCESS);
                    responseDTO.put("tags", groupTagResponseDTOS);
                    responseDTO.put("groupDetail", groupDetailResponseDTO);
                    return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
                } else {
                    responseDTO.put("responseMessage", ResponseMessage.GROUP_SAVE_FAIL);
                    return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
                }
            } else {
                responseDTO.put("responseMessage", ResponseMessage.GROUP_SAVE_FAIL);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
            }

        }
        catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.OK);
        }

    }

    @ApiOperation(value = "그룹 상세 정보 조회", notes =
            "groupIdx 값으로 그룹 상세 정보를 조회합니다.\n" +
                    "[Front] \n" +
                    "{groupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "{groupIdx(int), groupImg(String), groupName(String), private(boolean), tags(List(String)), creationDate(String)} ")
    @GetMapping("/{groupIdx}")
    public ResponseEntity<?> getGroupDTO(@ApiParam(value = "그룹 id", required = true) @PathVariable int groupIdx, @AuthenticationPrincipal UserEntity userInfo) {

        HashMap<String, Object> responseDTO = new HashMap<>();
        try {
            HashMap<String, Object> groupDTO = groupService.findGroupById(groupIdx);

            if (groupDTO != null) {
                responseDTO.put("responseMessage", ResponseMessage.GROUP_FIND_SUCCESS);
                responseDTO.put("groupDetail", groupDTO.get("groupDetail"));
                responseDTO.put("tags", groupDTO.get("tags"));
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
                    "{groupIdx(int)} \n\n" +
                    "[Back] \n")

    @PostMapping("/join")
    public ResponseEntity<?> groupJoin(@ApiParam(value = "유저 idx", required = true) @AuthenticationPrincipal UserEntity userInfo, @ApiParam(value = "그룹 idx", required = true) @RequestBody Map<String, Integer> requestDTO) {

        HashMap<String, Object> responseDTO = new HashMap<>();
        try {
            GroupUserRelationRequestDTO groupUserRelationRequestDTO = new GroupUserRelationRequestDTO(userInfo.getUserIdx(), requestDTO.get("groupIdx"));
            GroupUserRelationResponseDTO groupUserRelationResponseDTO = groupService.saveGroupUserRelation(groupUserRelationRequestDTO);

            if (groupUserRelationResponseDTO != null) {
                responseDTO.put("responseMessage", ResponseMessage.GROUP_JOIN_SUCCESS);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            } else {
                responseDTO.put("responseMessage", ResponseMessage.GROUP_JOIN_FAIL);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "그룹 리스트 조회", notes =
            "사용자가 소속된 그룹 리스트를 일기 최신 작성 순으로 조회합니다.\n" +
                    "[Front] \n" +
                    "path vatiable : opt(0/1) \n" +
                    "[Back] \n" +
                    "{List<GroupDetailResponse>}")
    @GetMapping("/list/{opt}")
    public ResponseEntity<?> getGroupList(@ApiParam(value = "유저 idx", required = true) @AuthenticationPrincipal UserEntity userInfo, @PathVariable("opt") int opt) {
        HashMap<String, Object> responseDTO = new HashMap<>();

        try {
            List<HashMap<String, Object>> groupDetailResponseDTOS = groupService.getGroupList(userInfo.getUserIdx(), opt);

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

    @ApiOperation(value = "그룹 정보 수정", notes =
            "groupIdx 그룹의 정보를 수정합니다.\n" +
                    "[Front] \n" +
                    "{groupIdx(int)) groupName(String), tags({}}), image(?)} \n\n" +
                    "[Back] \n" +
                    "{GroupDetailResponse}")
    @PutMapping
    public ResponseEntity<?> updateGroupDetail(@ApiParam(value = "groupIdx, groupName, tags, image", required = true) @RequestBody GroupDetailRequestDTO groupDetailRequestDTO, @AuthenticationPrincipal UserEntity userInfo) {

        HashMap<String, Object> responseDTO = new HashMap<>();

        try {
            HashMap<String, Object> groupDTO = groupService.updateGroupDetail(groupDetailRequestDTO);

            if (groupDTO != null) {
                responseDTO.put("groupDetail", groupDTO.get("groupDetail"));
                responseDTO.put("tags", groupDTO.get("tags"));
                responseDTO.put("responseMessage", ResponseMessage.GROUP_UPDATE_SUCCESS);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            } else {
                responseDTO.put("responseMessage", ResponseMessage.GROUP_UPDATE_FAIL);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
            }

        } catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "그룹 탈퇴", notes =
            "userIdx 사용자가 groupIdx 그룹을 탈퇴합니다.\n" +
                    "[Front]\n" +
                    "{groupIdx(int)}\n" +
                    "\n" +
                    "[Back]\n" +
                    "{}")
    @DeleteMapping("/{groupIdx}")
    public ResponseEntity<?> withdrawGroup(@ApiParam(value = "유저 idx", required = true) @AuthenticationPrincipal UserEntity userInfo, @ApiParam(value = "그룹 idx") @PathVariable("groupIdx") int groupIdx) {

        HashMap<String, Object> responseDTO = new HashMap<>();

        groupService.deleteGroupUser(userInfo.getUserIdx(), groupIdx);
        responseDTO.put("responseMessage", ResponseMessage.GROUP_USER_DELETE_SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "그룹 유저 리스트 조회", notes =
            "그룹의 유저 리스트를 조회합니다.\n" +
                    "[Front] \n" +
                    "{groupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "groupUserList : [{userIdx(int), nickname(String), img(String)] ")
    @GetMapping("user/{groupIdx}")
    public ResponseEntity<?> getGroupUserList(@ApiParam(value = "유저 idx", required = true) @AuthenticationPrincipal UserEntity userInfo, @ApiParam(value = "그룹 idx", required = true) @PathVariable int groupIdx) {
        HashMap<String, Object> responseDTO = new HashMap<>();
        try {
            List<HashMap<String, Object>> groupUserList = groupService.findGroupUser(userInfo.getUserIdx(), groupIdx);

            if (groupUserList.size() != 0) {
                responseDTO.put("responseMessage", ResponseMessage.GROUP_USER_FIND_SUCCESS);
                responseDTO.put("groupUserList", groupUserList);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            } else {
                responseDTO.put("responseMessage", ResponseMessage.NONE_DATA);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            }
        }
        catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }

    }

}
