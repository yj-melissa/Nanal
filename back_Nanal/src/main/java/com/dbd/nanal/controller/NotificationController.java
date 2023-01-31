package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.NotificationRequestDTO;
import com.dbd.nanal.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(tags = {"알림 관련 API"})
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("notification")
public class NotificationController {

    private final NoticeService noticeService;

    @ApiOperation(value = "알림 생성", notes =
            "알림을 생성합니다.\n" +
                    "[Front] \n" +
                    "{userIdx(int), requestUserIdx(int), requestGroupIdx(int), noticeType(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200) ")
    @PostMapping("")
    public ResponseEntity<?> saveNotice(@ApiParam(value = "알림 정보")@RequestBody NotificationRequestDTO notice) {
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            noticeService.saveNotice(notice);

            responseDTO.put("responseMessage", ResponseMessage.NOTICE_SAVE_SUCCESS);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
