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

import java.util.Date;
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
                    "JSON\n" +
                    "{groupIdx(int), groupImg(String), groupName(String), private(boolean), tags(List(String)), creationDate(String)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{groupIdx(int), groupImg(String), groupName(String), private(boolean), tags(List(String)), creationDate(String)} ")

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@ApiParam(value = "그룹 생성 정보") @RequestBody GroupDetailRequestDTO groupDetailRequestDTO, @AuthenticationPrincipal UserEntity userInfo) {
//    public ResponseEntity<?> save(@ApiParam(value = "그룹 생성 정보") @RequestBody GroupDetailRequestDTO groupDetailRequestDTO) {
        System.out.println("[그룹 저장] 유저 : idx " + userInfo.getUserIdx() + " name " + userInfo.getUsername());
        System.out.println("save");
        System.out.println(new Date().toString());

        HashMap<String, Object> responseDTO = new HashMap<>();

        try {
            // 1. group_detail table save, 결과 responseDTO에 저장
            GroupDetailResponseDTO groupDetailResponseDTO = groupService.saveGroup(groupDetailRequestDTO);

            // group_detail table save 성공, 반환
            if (groupDetailResponseDTO != null) {
                // 반환값에서 idx(auto_increment) 가져와서 request의 idx 채우기
                groupDetailRequestDTO.setGroupIdx(groupDetailResponseDTO.getGroupIdx());

                // 사용자부터 그룹에 가입!
                GroupUserRelationRequestDTO groupUserRelationRequestDTO = new GroupUserRelationRequestDTO(userInfo.getUserIdx(), groupDetailRequestDTO.getGroupIdx());
//                GroupUserRelationRequestDTO groupUserRelationRequestDTO = new GroupUserRelationRequestDTO(userIdx, groupDetailRequestDTO.getGroupIdx());
                groupService.saveGroupUserRelation(groupUserRelationRequestDTO);

                // 2. group_tag table save, 결과 responseDTO에 저장
                List<GroupTagResponseDTO> groupTagResponseDTOS = groupService.saveGroupTags(groupDetailRequestDTO);
                // 저장 성공
                if (groupTagResponseDTOS != null) {
                    // FRONT - responseDTO(그룹 상세 정보) 전달
                    responseDTO.put("responseMessage", ResponseMessage.GROUP_SAVE_SUCCESS);
                    responseDTO.put("tags", groupTagResponseDTOS);
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
    public ResponseEntity<?> getGroupDTO(@ApiParam(value = "그룹 id", required = true) @PathVariable int groupIdx, @AuthenticationPrincipal UserEntity userInfo) {
        System.out.println("[그룹 정보] 유저 : idx " + userInfo.getUserIdx() + " name " + userInfo.getUsername());

        HashMap<String, Object> responseDTO = new HashMap<>();
        try {
            HashMap<String, Object> groupDTO = groupService.findGroupById(groupIdx);

            // 반환 성공
            if (groupDTO != null) {
                // FRONT로 responseDTO(그룹 상세 정보) 전달
                // 결과 넣기!!
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
                    "JSON\n" +
                    "{groupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{success(boolean)}")

    @PostMapping("/join")
//    public ResponseEntity<?> groupJoin(@ApiParam(value = "groupIdx, userIdx", required = true) @RequestBody Map<String, Integer> requestDTO) {
    public ResponseEntity<?> groupJoin(@ApiParam(value = "유저 idx", required = true) @AuthenticationPrincipal UserEntity userInfo, @ApiParam(value = "그룹 idx", required = true) @RequestBody Map<String, Integer> requestDTO) {
        System.out.println("[그룹 가입] 유저 : idx " + userInfo.getUserIdx() + " name " + userInfo.getUsername());

        HashMap<String, Object> responseDTO = new HashMap<>();
        try {

            GroupUserRelationRequestDTO groupUserRelationRequestDTO = new GroupUserRelationRequestDTO(userInfo.getUserIdx(), requestDTO.get("groupIdx"));
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
                    "{} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{List<GroupDetailResponse>}")
//    @GetMapping("/list/{userIdx}")
    @GetMapping("/list")
//    public ResponseEntity<?> getGroupList(@ApiParam(value = "유저 idx", required = true) @PathVariable int userIdx) {
    public ResponseEntity<?> getGroupList(@ApiParam(value = "유저 idx", required = true) @AuthenticationPrincipal UserEntity userInfo) {
        HashMap<String, Object> responseDTO = new HashMap<>();

        System.out.println("[그룹 리스트] 유저 : idx " + userInfo.getUserIdx() + " name " + userInfo.getUsername());

        try {
            // group_user_relation 테이블에서 userIdx가 포함된 group찾기
            List<HashMap<String, Object>> groupDetailResponseDTOS =
                    groupService.getGroupList(userInfo.getUserIdx());
//            List<HashMap<String, Object>> groupDetailResponseDTOS =
//                    groupService.getGroupList(userInfo.getUserIdx());

            System.out.println("list : " + groupDetailResponseDTOS.size());

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
                    "JSON\n" +
                    "{groupIdx(int)) groupName(String), tags(tagIdx(int), String(content)), image(?)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{GroupDetailResponse}")
    @PutMapping
    public ResponseEntity<?> updateGroupDetail(@ApiParam(value = "groupIdx, groupName, tags, image", required = true) @RequestBody GroupDetailRequestDTO groupDetailRequestDTO, @AuthenticationPrincipal UserEntity userInfo) {
        System.out.println("[그룹 수정] 유저 : idx " + userInfo.getUserIdx() + " name " + userInfo.getUsername());

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
                    "JSON\n" +
                    "{groupIdx(int)}\n" +
                    "\n" +
                    "[Back]\n" +
                    "JSON\n" +
                    "{}")
//    @DeleteMapping("/{userIdx}/{groupIdx}")
    @DeleteMapping("/{groupIdx}")
//    public ResponseEntity<?> withdrawGroup(@ApiParam(value = "사용자 idx") @PathVariable("userIdx") int userIdx, @ApiParam(value = "그룹 idx") @PathVariable("groupIdx") int groupIdx) {
    public ResponseEntity<?> withdrawGroup(@ApiParam(value = "유저 idx", required = true) @AuthenticationPrincipal UserEntity userInfo, @ApiParam(value = "그룹 idx") @PathVariable("groupIdx") int groupIdx) {
        System.out.println("[그룹 탈퇴] 유저 : idx " + userInfo.getUserIdx() + " name " + userInfo.getUsername());

        HashMap<String, Object> responseDTO = new HashMap<>();

        groupService.deleteGroupUser(userInfo.getUserIdx(), groupIdx);
        responseDTO.put("responseMessage", ResponseMessage.GROUP_USER_DELETE_SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }


    @ApiOperation(value = "그룹 유저 리스트 조회", notes =
            "그룹의 유저 리스트를 조회합니다.\n" +
                    "[Front] \n" +
                    "JSON\n" +
                    "{groupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "groupUserList : [{userIdx(int), nickName(String), img(String)] ")
//    @GetMapping("list/{userIdx}")
    @GetMapping("user/{groupIdx}")
//    public ResponseEntity<?> getFriendList(@ApiParam(value = "사용자 idx", required = true) @PathVariable int userIdx) {
    public ResponseEntity<?> getGroupUserList(@ApiParam(value = "유저 idx", required = true) @AuthenticationPrincipal UserEntity userInfo, @ApiParam(value = "그룹 idx", required = true) @PathVariable int groupIdx) {
        System.out.println("[그룹 유저 리스트] 유저 : idx " + userInfo.getUserIdx() + " name " + userInfo.getUsername());
        HashMap<String, Object> responseDTO = new HashMap<>();
        try {
            List<HashMap<String, Object>> groupUserList = groupService.findGroupUser(userInfo.getUserIdx(), groupIdx);
            // 반환 성공
            if (groupUserList.size() != 0) {
                HashMap<String, Object> response = new HashMap<>();
                responseDTO.put("responseMessage", ResponseMessage.GROUP_USER_FIND_SUCCESS);
                responseDTO.put("groupUserList", groupUserList);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            } else {
                responseDTO.put("responseMessage", ResponseMessage.NONE_DATA);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            }
        }
        // Exception 발생
        catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }

    }

}
