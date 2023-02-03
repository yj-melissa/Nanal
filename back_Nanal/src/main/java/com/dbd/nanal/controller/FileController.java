package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.PaintingRequestDTO;
import com.dbd.nanal.model.PaintingEntity;
import com.dbd.nanal.service.FileService;
import com.dbd.nanal.service.PaintingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    @ApiOperation(value = "그림 저장", notes =
            "달리에서 생성된 그림을 저장합니다.\n" +
                    "[Front] \n" +
                    "JSON\n" +
                    "{multipartFile(MultipartFile), dto..} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{pictureIdx, picturePath, creationDate, pictureTitle, fileSize} ")

    @Transactional
    @PostMapping(consumes = {"multipart/form-data"
            , "application/json"})
    public ResponseEntity<?> paintingSave(@ApiParam(value = "달리 이미지") @RequestPart(value = "multipartFile") MultipartFile multipartFile, @ApiParam(value = "받을 dto") @RequestPart(name = "value")
    PaintingRequestDTO paintingRequestDTO) {

        HashMap<String, Object> responseDTO = new HashMap<>();

        try {
            paintingRequestDTO.setFile(multipartFile);
            PaintingEntity painting = fileService.paintingSave(paintingRequestDTO);

            if (painting != null) {
                responseDTO.put("responseMessage", ResponseMessage.PAINTING_SAVE_SUCCESS);
                responseDTO.put("painting", painting);
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

    @GetMapping(value = "/{paintingIdx}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> paintingFind(@ApiParam(value = "이미지 idx") @PathVariable("paintingIdx") int paintingIdx) throws IOException {
        Optional<PaintingEntity> paintingEntity = paintingService.findById(paintingIdx);

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

}
