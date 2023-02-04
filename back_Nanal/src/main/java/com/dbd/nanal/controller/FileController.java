package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.PaintingRequestDTO;
import com.dbd.nanal.dto.PaintingResponseDTO;
import com.dbd.nanal.model.PaintingEntity;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.service.FileService;
import com.dbd.nanal.service.GroupService;
import com.dbd.nanal.service.PaintingService;
import com.dbd.nanal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;


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


    @ApiOperation(value = "그림 불러오기", notes =
                    "저장된 이미지를 불러옵니다.\n" +
                    "[Front] \n" +
                    "[Back] \n" +
                    "{File} ")
    @GetMapping(value = "/{paintingIdx}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> paintingFind(@ApiParam(value = "이미지 idx") @PathVariable("paintingIdx") int paintingIdx) throws IOException {
        Optional<PaintingEntity> paintingEntity = paintingService.findById(paintingIdx);
        HashMap<String, Object> responseDTO = new HashMap<>();

        if (paintingEntity.isPresent()) {
            UrlResource urlResource = new UrlResource("file:" + paintingEntity.get().getPicturePath());

            String encodedUploadFileName = UriUtils.encode(paintingEntity.get().getPictureTitle(), StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(urlResource);
        }

        return new ResponseEntity<>(DefaultRes.res(500, ResponseMessage.INTERNAL_SERVER_ERROR), HttpStatus.OK);
    }

    @ApiOperation(value = "그림 저장", notes =
                    "그룹을 생성할 때 선택한 그림을 저장합니다.\n" +
                    "[Front] \n" +
                    "그룹을 생성할 때 선택한 그림을 저장합니다.\n" +
                    "{multipartFile(MultipartFile), value : {\"groupIdx : 2\"}\n" +
                    "유저가 선택한 프로필 사진을 저장합니다.\n" +
                    "{multipartFile(MultipartFile), value : { } " +
                    " \n\n" +
                    "[Back] \n" +
                    "{pictureIdx, pictureTitle} ")

    @Transactional
    @PostMapping(consumes = {"multipart/form-data"
            , "application/json"})
    public ResponseEntity<?> paintingSave(@ApiParam(value = "달리 이미지") @RequestPart(value = "multipartFile") MultipartFile multipartFile, @ApiParam(value = "받을 dto", required = false) @RequestPart(name = "value")
    PaintingRequestDTO paintingRequestDTO, @AuthenticationPrincipal UserEntity userInfo) {
//    public ResponseEntity<?> paintingSave(@ApiParam(value = "달리 이미지") @RequestPart(value = "multipartFile") MultipartFile multipartFile, @ApiParam(value = "받을 dto",required = false) @RequestPart(name = "value")
//    PaintingRequestDTO paintingRequestDTO) {


        HashMap<String, Object> responseDTO = new HashMap<>();

        try {
            // dto에 파일을 넣는다.
            paintingRequestDTO.setFile(multipartFile);
            // 파일을 저장한다. // 파일 인덱스를 받는다.
            PaintingResponseDTO painting = fileService.paintingSave(paintingRequestDTO);

            if (paintingRequestDTO.getGroupIdx() != 0) {
                // group idx를 받았으면 group 인덱스의 이미지 idx를 변경한다.
                groupService.updateGroupImg(paintingRequestDTO.getGroupIdx(), painting.getPictureIdx());
            } else {
                // group idx를 받지 않았으면 user 인덱스의 이미지 idx를 변경한다.
                userService.updateUserImg(userInfo.getUserIdx(), painting.getPictureIdx());
            }


            // 나머지는 달리. diary 인덱스의 이미지 idx를 변경한다. --> 달리는 url로 하기!!!

            // 파일 인덱스를 리턴한다.


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

}
