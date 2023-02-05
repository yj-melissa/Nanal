package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(tags = {"검색 관련 API"})
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("search")
public class SearchController {

    private final SearchService searchService;

    // 사용자 ID 검색
    @ApiOperation(value = "사용자 ID 검색", notes =
            "사용자를 검색합니다.\n" +
                    "[Front] \n" +
                    "{id(String)} \n\n" +
                    "[Back] \n" +
                    "{userIdx(int), name(String), email(String), user_id(String), nickanme(String(} ")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserId(@ApiParam(value = "user id")@PathVariable("id") String id, @AuthenticationPrincipal UserEntity userInfo){
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            responseDTO.put("responseMessage", ResponseMessage.FRIEND_FIND_SUCCESS);
            responseDTO.put("userInfo", searchService.getUser(id, userInfo.getUserIdx()));
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.FRIEND_FIND_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    // 일기 내용 검색
    @ApiOperation(value = "일기 내용 검색", notes =
            "사용자를 검색합니다.\n" +
                    "[Front] \n" +
                    "{content(String)} \n\n" +
                    "[Back] \n" +
                    "{diaryIdx(int), userIdx(int), creationDate(Date), content(String), nickName(String), groupIdx(int), groupName(String)}")
    @GetMapping("/diary/{content}")
    public ResponseEntity<?> getDiaryContent(@ApiParam(value = "diary content")@PathVariable("content") String content, @AuthenticationPrincipal UserEntity userInfo){
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            responseDTO.put("responseMessage", ResponseMessage.DIARY_GET_SUCCESS);
            responseDTO.put("userInfo", searchService.getDiaryContent(content, userInfo.getUserIdx()));
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_GET_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }
}
