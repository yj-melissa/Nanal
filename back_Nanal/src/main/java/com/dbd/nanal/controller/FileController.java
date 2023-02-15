package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.PaintingRequestDTO;
import com.dbd.nanal.dto.PaintingResponseDTO;
import com.dbd.nanal.handler.FileHandler;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.service.FileService;
import com.dbd.nanal.service.GroupService;
import com.dbd.nanal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;


@Api(tags = {"파일 관련 API"})
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;
    private final GroupService groupService;


    @ApiOperation(value = "그림 등록", hidden = true, notes =
            "이미지를 등록합니다.\n" +
                    "[1] 기본 이미지를 등록합니다.\n" +
                    "[2] 새로운 이미지를 등록합니다.\n" +
                    "[Front] \n" +
                    "user 프로필 사진 등록할 때 \n" +
                    "data : formdata, value : {}, headers:{'Content-Type' : 'multipart/form-data'}\n" +
                    "group 프로필 사진 등록할 때 \n" +
                    "data : formdata, value : {'groupIdx' : 1}, headers:{'Content-Type' : 'multipart/form-data'}\n" +
                    "[Back] \n" +
                    "{String imgUrl} ")

    @Transactional
    @PostMapping(value = "/s3", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveImg(@ApiParam(value = "사용자 등록 이미지") @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile, @ApiParam(value = "{\"groupIdx\"} or {}", required = false) @RequestPart(name = "value")
    PaintingRequestDTO paintingRequestDTO, @AuthenticationPrincipal UserEntity userInfo) throws IOException {
        HashMap<String, Object> responseDTO = new HashMap<>();

        String imgUrl = null;
        PaintingResponseDTO painting;

        if (multipartFile == null) {
            // [1] 기본 이미지 설정 - 사용자 미선택

            // 기본 이미지 설정
            paintingRequestDTO.init();// requestDTO 기본 이미지로 설정

            // DB - painting Entity에 반영
            painting = fileService.paintingSave(paintingRequestDTO);
            // DB - group Entity에 반영

            if (paintingRequestDTO.getGroupIdx() != 0) {
                // group idx를 받았으면 group 인덱스의 이미지 idx를 변경한다.
                System.out.println("그룹 이미지 수정 idx : : " + paintingRequestDTO.getGroupIdx());
                groupService.updateGroupImg(paintingRequestDTO.getGroupIdx(), painting.getPictureIdx(), paintingRequestDTO.getImgUrl());
            } else {
                // group idx를 받지 않았으면 user 인덱스의 이미지 idx를 변경한다.
                System.out.println("기본 이미지 개인 프로필 이미지 수정 idx : : " + userInfo.getUserIdx());
                userService.updateUserImg(userInfo.getUserIdx(), painting.getPictureIdx(), paintingRequestDTO.getImgUrl());
            }
        } else {
            // [2] 새로운 이미지 설정 - 사용자 선택
            System.out.println("groupidx : " + paintingRequestDTO.getGroupIdx());
            // (1) multipartfile을 file로 변환
            FileHandler handler = new FileHandler();
            // (1.1) requestDTO에 multipartfile 담기
            paintingRequestDTO.setMultipartFile(multipartFile);
            // (1.2) requestDTO에 multipartfile -> file로 변환해서 file 담기
            paintingRequestDTO = handler.parseFile(paintingRequestDTO);

            // (1.3) file s3에 저장하고 URL 반환받기
            imgUrl = fileService.saveToS3(paintingRequestDTO.getFile());
            // (1.4) requestDTD에 URL 저장하기
            paintingRequestDTO.setImgUrl(imgUrl);


            // (2) DB - painting Entity에 반영
            // (2.1)
            painting = fileService.paintingSave(paintingRequestDTO);

            // (2.2) painting
//                painting.setImgUrl(paintingRequestDTO.getImgUrl());
//                painting = fileService.paintingUpdate(paintingRequestDTO); // painting table update

            if (paintingRequestDTO.getGroupIdx() != 0) {
                // group idx를 받았으면 group 인덱스의 이미지 idx를 변경한다.
                System.out.println("그룹 이미지 수정 idx : : " + paintingRequestDTO.getGroupIdx());
                groupService.updateGroupImg(paintingRequestDTO.getGroupIdx(), painting.getPictureIdx(), imgUrl);
            } else {
                // group idx를 받지 않았으면 user 인덱스의 이미지 idx를 변경한다.
                System.out.println("개인 프로필 이미지 수정 idx : : " + userInfo.getUserIdx());
                userService.updateUserImg(userInfo.getUserIdx(), painting.getPictureIdx(), imgUrl);
            }
        }

        if (painting != null) {
            responseDTO.put("responseMessage", ResponseMessage.PAINTING_SAVE_SUCCESS);
            responseDTO.put("pictureInfo", painting);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        } else {
            responseDTO.put("responseMessage", ResponseMessage.PAINTING_SAVE_FAIL);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }
    }


    @ApiOperation(value = "그림 수정", hidden = true, notes =
            "이미지를 수정합니다.\n" +
                    "[Front] \n" +
                    "user 프로필 사진 수정할 때 \n" +
                    "data : formdata, value : {}, headers:{'Content-Type' : 'multipart/form-data'}\n" +
                    "group 프로필 사진 등록할 때 \n" +
                    "data : formdata, value : {groupImgIdx, groupIdx}, headers:{'Content-Type' : 'multipart/form-data'}\n" +
                    "[Back] \n" +
                    "{String imgUrl} ")
    @Transactional
    @PutMapping(value = "/s3", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateImg(@ApiParam(value = "사용자 등록 이미지") @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile, @ApiParam(value = "{\"groupImgIdx\"}", required = false) @RequestPart(name = "value", required = false)
    PaintingRequestDTO paintingRequestDTO, @AuthenticationPrincipal UserEntity userInfo) throws IOException {
        HashMap<String, Object> responseDTO = new HashMap<>();

        PaintingResponseDTO painting;

        if (multipartFile == null) {
            System.out.println("선택 x");
            // 기본 이미지로 변경
            paintingRequestDTO.init();// imgUrl에 기본 url도 다시 넣기 //
            paintingRequestDTO.setGroupImgIdx(0); // 기본 이미지 idx로 변경
            painting = fileService.paintingSave(paintingRequestDTO);

            // 그룹 or 사용자
            if (paintingRequestDTO.getGroupIdx() != 0) {
                // group idx를 받았으면 group 인덱스의 이미지 idx를 변경한다.
                groupService.updateGroupImg(paintingRequestDTO.getGroupIdx(), painting.getPictureIdx(), paintingRequestDTO.getImgUrl());
            } else {
                // group idx를 받지 않았으면 user 인덱스의 이미지 idx를 변경한다.
                userService.updateUserImg(userInfo.getUserIdx(), painting.getPictureIdx(), paintingRequestDTO.getImgUrl());
            }
        } else {
            // new 이미지 저장
            System.out.println("선택 o");
            FileHandler handler = new FileHandler();

            System.out.println("multipartfile : ");
            System.out.println(multipartFile);

            paintingRequestDTO.setMultipartFile(multipartFile); // input data dto에 합치기
            paintingRequestDTO = handler.parseFile(paintingRequestDTO);// file로 변환해서 dto에 넣기
            String imgUrl = fileService.saveToS3(paintingRequestDTO.getFile());// file s3에 저장하고 링크 반환

            System.out.println("s3에 저장완료");

            paintingRequestDTO.setImgUrl(imgUrl); // dto에 imgUrl 저장
            painting = fileService.paintingSave(paintingRequestDTO); // painting table update

            System.out.println("db에 저장 완료");

            // 그룹 or 사용자
            if (paintingRequestDTO.getGroupIdx() != 0) {
                // group idx를 받았으면 group 인덱스의 이미지 idx를 변경한다.
                groupService.updateGroupImg(paintingRequestDTO.getGroupIdx(), painting.getPictureIdx(), imgUrl); // groupDetail table update
            } else {
                // group idx를 받지 않았으면 user 인덱스의 이미지 idx를 변경한다.
                userService.updateUserImg(userInfo.getUserIdx(), painting.getPictureIdx(), imgUrl);
            }
        }

        if (painting != null) {
            responseDTO.put("responseMessage", ResponseMessage.PAINTING_UPDATE_SUCCESS);
            responseDTO.put("pictureInfo", painting);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        } else {
            responseDTO.put("responseMessage", ResponseMessage.PAINTING_UPDATE_FAIL);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }

    }

}
