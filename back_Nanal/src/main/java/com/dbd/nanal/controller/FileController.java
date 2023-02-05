package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.DiaryResponseDTO;
import com.dbd.nanal.dto.PaintingRequestDTO;
import com.dbd.nanal.dto.PaintingResponseDTO;
import com.dbd.nanal.handler.FileHandler;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final PaintingService paintingService;
    private final UserService userService;
    private final GroupService groupService;
    private final DiaryService diaryService;

    //    @ApiOperation(value = "그림 불러오기", notes =
//                    "저장된 이미지를 불러옵니다.\n" +
//                    "[Front] \n" +
//                    "[Back] \n" +
//                    "{File} ")
//    @GetMapping(value = "/{paintingIdx}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public Resource paintingView(@ApiParam(value = "이미지 idx") @PathVariable("paintingIdx") int paintingIdx) throws IOException {
//        Optional<PaintingEntity> paintingEntity = paintingService.findById(paintingIdx);
//        HashMap<String, Object> responseDTO = new HashMap<>();
//
//        if (paintingEntity.isPresent()) {
////            UrlResource urlResource = new UrlResource("file:" + paintingEntity.get().getPicturePath());
////
////            String encodedUploadFileName = UriUtils.encode(paintingEntity.get().getPictureTitle(), StandardCharsets.UTF_8);
////            String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
//
//            return new UrlResource("file:" + paintingEntity.get().getPicturePath());
//
//        }
//
////        return new ResponseEntity<>(DefaultRes.res(500, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.OK);
//        return null;
//    }
//
//    @ApiOperation(value = "그림 다운로드", notes =
//                    "저장된 이미지를 불러옵니다.\n" +
//                    "[Front] \n" +
//                    "[Back] \n" +
//                    "{File} ")
//    @GetMapping(value = "/{paintingIdx}/save", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<?> paintingDownload(@ApiParam(value = "이미지 idx") @PathVariable("paintingIdx") int paintingIdx) throws IOException {
//        Optional<PaintingEntity> paintingEntity = paintingService.findById(paintingIdx);
//        HashMap<String, Object> responseDTO = new HashMap<>();
//
//        if (paintingEntity.isPresent()) {
//            UrlResource urlResource = new UrlResource("file:" + paintingEntity.get().getPicturePath());
//
//            String encodedUploadFileName = UriUtils.encode(paintingEntity.get().getPictureTitle(), StandardCharsets.UTF_8);
//            String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                    .body(urlResource);
//        }
//
//        return new ResponseEntity<>(DefaultRes.res(500, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "그림 저장", notes =
//                    "그룹을 생성할 때 선택한 그림을 저장합니다.\n" +
//                    "[Front] \n" +
//                    "그룹을 생성할 때 선택한 그림을 저장합니다.\n" +
//                    "{multipartFile(MultipartFile), value : {\"groupIdx : 2\"}\n" +
//                    "유저가 선택한 프로필 사진을 저장합니다.\n" +
//                    "{multipartFile(MultipartFile), value : { } " +
//                    " \n\n" +
//                    "[Back] \n" +
//                    "{pictureIdx, pictureTitle} ")
//
//    @Transactional
//    @PostMapping(consumes = {"multipart/form-data"
//            , "application/json"})
//    public ResponseEntity<?> paintingSave(@ApiParam(value = "달리 이미지") @RequestPart(value = "multipartFile") MultipartFile multipartFile, @ApiParam(value = "받을 dto", required = false) @RequestPart(name = "value")
//    PaintingRequestDTO paintingRequestDTO, @AuthenticationPrincipal UserEntity userInfo) {
////    public ResponseEntity<?> paintingSave(@ApiParam(value = "달리 이미지") @RequestPart(value = "multipartFile") MultipartFile multipartFile, @ApiParam(value = "받을 dto",required = false) @RequestPart(name = "value")
////    PaintingRequestDTO paintingRequestDTO) {
//
//
//        HashMap<String, Object> responseDTO = new HashMap<>();
//
//        try {
//            // dto에 파일을 넣는다.
//            paintingRequestDTO.setFile(multipartFile);
//            // 파일을 저장한다. // 파일 인덱스를 받는다.
//            PaintingResponseDTO painting = fileService.paintingSave(paintingRequestDTO);
//
//            if (paintingRequestDTO.getGroupIdx() != 0) {
//                // group idx를 받았으면 group 인덱스의 이미지 idx를 변경한다.
//                groupService.updateGroupImg(paintingRequestDTO.getGroupIdx(), painting.getPictureIdx());
//            } else {
//                // group idx를 받지 않았으면 user 인덱스의 이미지 idx를 변경한다.
//                userService.updateUserImg(userInfo.getUserIdx(), painting.getPictureIdx());
//            }
//
//
//            // 나머지는 달리. diary 인덱스의 이미지 idx를 변경한다. --> 달리는 url로 하기!!!
//
//            // 파일 인덱스를 리턴한다.
//
//
//            if (painting != null) {
//                responseDTO.put("responseMessage", ResponseMessage.PAINTING_SAVE_SUCCESS);
//                responseDTO.put("pictureInfo", painting);
//                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
//            } else {
//                responseDTO.put("responseMessage", ResponseMessage.PAINTING_SAVE_FAIL);
//                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
//            }
//        }
//        // Exception 발생
//        catch (Exception e) {
//            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
//            return new ResponseEntity<>(DefaultRes.res(500, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.OK);
//        }
//
//    }


    @ApiOperation(value = "그림 저장", notes =
            "이미지를 s3에 저장합니다..\n" +
                    "[Front] \n" +
                    "user 프로필 사진 등록할 때 \n" +
                    "data : formdata, value : {}, headers:{'Content-Type' : 'multipart/form-data'}\n" +
                    "group 프로필 사진 등록할 때 \n" +
                    "data : formdata, value : {'groupIdx' : 1}, headers:{'Content-Type' : 'multipart/form-data'}\n" +
                    "[Back] \n" +
                    "{String imgUrl} ")
    @Transactional
    @PostMapping(value = "/s3", consumes = {"multipart/form-data"
            , "application/json"})
    public ResponseEntity<?> saveToS3(@ApiParam(value = "사용자 등록 이미지") @RequestPart(value = "multipartFile") MultipartFile multipartFile, @ApiParam(value = "받을 dto", required = false) @RequestPart(name = "value")
    PaintingRequestDTO paintingRequestDTO, @AuthenticationPrincipal UserEntity userInfo) throws IOException {
        HashMap<String, Object> responseDTO = new HashMap<>();

        try {
            // 1. multipartfile을 file로 변환해서 s3에 저장
            FileHandler handler = new FileHandler();
            paintingRequestDTO.setMultipartFile(multipartFile);
            paintingRequestDTO = handler.parseFile(paintingRequestDTO);
            String imgUrl = fileService.saveToS3(paintingRequestDTO.getFile());
            // 2. db에 저장
            paintingRequestDTO.setImgUrl(imgUrl);
            PaintingResponseDTO painting = fileService.paintingSave(paintingRequestDTO);
            painting.setImgUrl(imgUrl);

            if (paintingRequestDTO.getGroupIdx() != 0) {
                // group idx를 받았으면 group 인덱스의 이미지 idx를 변경한다.
                groupService.updateGroupImg(paintingRequestDTO.getGroupIdx(), painting.getPictureIdx(), imgUrl);
            } else {
                // group idx를 받지 않았으면 user 인덱스의 이미지 idx를 변경한다.
                userService.updateUserImg(userInfo.getUserIdx(), imgUrl);
//                userService.updateUserImg(userInfo.getUserIdx(),  painting.getPictureIdx(), imgUrl);
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
        // Exception 발생
        catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "달리 저장", notes =
            "이미지를 s3에 저장합니다..\n" +
                    "[Front] \n" +
                    "user 프로필 사진 등록할 때 \n" +
                    "data : formdata, value : {}, headers:{'Content-Type' : 'multipart/form-data'}\n" +
                    "group 프로필 사진 등록할 때 \n" +
                    "data : formdata, value : {'groupIdx' : 1}, headers:{'Content-Type' : 'multipart/form-data'}\n" +
                    "[Back] \n" +
                    "{String imgUrl} ")
    @Transactional
    @PostMapping(value = "/s3/{diaryIdx}")
    public ResponseEntity<?> saveDalleToS3(@ApiParam(value = "달리 이미지 url", required = true) @RequestBody HashMap<String, Object> map) throws IOException {
        HashMap<String, Object> responseDTO = new HashMap<>();

        try {

            String url = (String) map.get("url");
            int diaryIdx = (int) map.get("diaryIdx");

            // 2. db에 저장
//            diaryService.updateDiaryImg(diaryIdx, url);
//            PaintingResponseDTO painting = fileService.paintingSave(paintingRequestDTO);
            // 지나간 일기 그림은 그냥 못봤으면

//            if (painting != null) {
//                responseDTO.put("responseMessage", ResponseMessage.PAINTING_SAVE_SUCCESS);
//                responseDTO.put("pictureInfo", painting);
//                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
//            } else {
//                responseDTO.put("responseMessage", ResponseMessage.PAINTING_SAVE_FAIL);
//                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
//            }
        }
        // Exception 발생
        catch (Exception e) {
            responseDTO.put("responseMessage", ResponseMessage.EXCEPTION);
            return new ResponseEntity<>(DefaultRes.res(500, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.OK);
        }
        return null;
    }


}
