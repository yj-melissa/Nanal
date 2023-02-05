package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.NotificationRequestDTO;
import com.dbd.nanal.dto.NotificationResponseDTO;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = {"알림 관련 API"})
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("notification")
public class NotificationController {

    private final NoticeService noticeService;

    @ApiOperation(value = "친구 추가 알림 생성", notes =
            "친구 추가 알림을 생성합니다.\n" +
                    "[Front] \n" +
                    "{userIdx(list)} \n\n" +
                    "[Back] \n" +
                    "ok(200) ")
    @PostMapping("/friend")
    public ResponseEntity<?> saveFriendNotice(@ApiParam(value = "알림 정보")@RequestBody NotificationRequestDTO notice, @AuthenticationPrincipal UserEntity userInfo) {
        HashMap<String, Object> responseDTO = new HashMap<>();
        System.out.println(notice.toString());
        try{
            notice.setRequest_user_idx(userInfo.getUserIdx());
            notice.setNotice_type(0);
            noticeService.saveFriendNotice(notice);
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_SAVE_SUCCESS);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "그룹 초대 알림 생성", notes =
            "그룹 초대 알림을 생성합니다.\n" +
                    "[Front] \n" +
                    "{userIdx(list), requestGroupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200) ")
    @PostMapping("/group")
    public ResponseEntity<?> saveGroupNotice(@ApiParam(value = "알림 정보")@RequestBody NotificationRequestDTO notice, @AuthenticationPrincipal UserEntity userInfo) {
        HashMap<String, Object> responseDTO = new HashMap<>();
        System.out.println(notice.toString());
        try{
            notice.setRequest_user_idx(userInfo.getUserIdx());
            notice.setNotice_type(1);
            noticeService.saveGroupNotice(notice);
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_SAVE_SUCCESS);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_SAVE_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);

        }
    }

    @ApiOperation(value = "댓글 알림 생성", notes =
            "댓글 알림을 생성합니다.\n" +
                    "[Front] \n" +
                    "{userIdx([]), requestDiaryIdx(int) ,requestGroupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200) ")
    @PostMapping("/comment")
    public ResponseEntity<?> saveCommentNotice(@ApiParam(value = "알림 정보")@RequestBody NotificationRequestDTO notice, @AuthenticationPrincipal UserEntity userInfo) {
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            notice.setRequest_user_idx(userInfo.getUserIdx());
            notice.setNotice_type(3);
            noticeService.saveCommentNotice(notice);
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_SAVE_SUCCESS);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_SAVE_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "새로운 일기 알림 생성", notes =
            "새로운 일기 알림을 생성합니다.\n" +
                    "[Front] \n" +
                    "{userIdx([]), requestDiaryIdx(int) ,requestGroupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200) ")
    @PostMapping("/diary")
    public ResponseEntity<?> saveDiaryNotice(@ApiParam(value = "알림 정보")@RequestBody NotificationRequestDTO notice, @AuthenticationPrincipal UserEntity userInfo) {
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            notice.setRequest_user_idx(userInfo.getUserIdx());
            notice.setNotice_type(2);
            noticeService.saveDiaryNotice(notice);
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_SAVE_SUCCESS);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_SAVE_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);

        }
    }

    @ApiOperation(value = "userIdx로 알림 조회", notes =
            "알림을 조회합니다.\n" +
                    "[Front] \n" +
                    "{userIdx(int)} \n\n" +
                    "[Back] \n" +
                    "[{requestUserIdx(보낸사람), requestGroupIdx(그룹 번호), requestDiaryIdx(일기 번호), noticeType(알림종류), content(알림 내용, isChecked(읽음여부)}] ")
//    @GetMapping("/{userIdx}")
    @GetMapping("")
//    public ResponseEntity<?> getNotice(@ApiParam(value = "유저 id")@PathVariable("userIdx") int userIdx){
    public ResponseEntity<?> getNotice(@ApiParam(value = "유저 id") @AuthenticationPrincipal UserEntity userInfo){
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            List<NotificationResponseDTO> notificationResponseDTOList =noticeService.getNotice(userInfo.getUserIdx());
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_GET_SUCCESS);
            responseDTO.put("diary", notificationResponseDTOList);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_GET_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "알림 삭제", notes =
            "알림을 삭제합니다.\n" +
                    "[Front] \n" +
                    "{noticeIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @DeleteMapping("/{noticeIdx}")
    public ResponseEntity<?> deleteDiary( @ApiParam(value="알림 id") @PathVariable("noticeIdx") int noticeIdx){
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            noticeService.deleteNotice(noticeIdx);
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_DELETE_SUCCESS);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_DELETE_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "알림 읽음 처리", notes =
            "알림 읽음 처리합니다.\n" +
                    "[Front] \n" +
                    "{noticeIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @GetMapping("/{noticeIdx}")
    public ResponseEntity<?> saveNoticeCheck(@ApiParam(value = "알림 id")  @PathVariable("noticeIdx") int noticeIdx){
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            noticeService.saveNoticeCheck(noticeIdx);
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_GET_SUCCESS);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.NOTICE_GET_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }
}
