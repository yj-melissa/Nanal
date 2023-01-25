package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.DiaryRequestDTO;
import com.dbd.nanal.dto.DiaryResponseDTO;
import com.dbd.nanal.dto.GroupDiaryRelationDTO;
import com.dbd.nanal.service.DiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = {"Diary 관련 API"})
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("diary")
public class DiaryController {

    private final DiaryService diaryService;

    HashMap<String, Object> responseDTO = new HashMap<>();

    @ApiOperation(value = "일기 생성", notes =
            "일기를 생성합니다.\n" +
                    "[Front] \n" +
                    "{userIdx(int), creationDate(Date), groupIdx(List<Integer>)} \n\n" +
                    "[Back] \n" +
                    "{diaryIdx(int), userIdx(int), creationDate(Date), content(String), picture(String), music(int), emo(String)} ")
    @PostMapping("")
    public ResponseEntity<?> writeDiary(@ApiParam(value = "일기 정보")@RequestBody DiaryRequestDTO diary) {
        try{
            //diary keyword analyze
            //picture
            //music

            //save diary
            DiaryResponseDTO diaryResponseDTO=diaryService.save(diary);
            int diaryIdx=diaryResponseDTO.getDiaryIdx();
            System.out.println(diary.getContent());
            //save diary-group
            for(int i=0; i<diary.getGroupIdxList().size(); i++){
                GroupDiaryRelationDTO groupDiaryRelationDTO=new GroupDiaryRelationDTO(diaryIdx, diary.getGroupIdxList().get(i));
                diaryService.saveDiaryGroup(groupDiaryRelationDTO);
            }
            responseDTO.put("responseMessage", ResponseMessage.DIARY_SAVE_SUCCESS);
            responseDTO.put("diary", diaryResponseDTO);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "diaryIdx로 일기 내용 조회", notes =
            "일기 내용을 조회합니다.\n" +
                    "[Front] \n" +
                    "{diaryIdx(int)} \n\n" +
                    "[Back] \n" +
                    "{diaryIdx(int), userIdx(int), creationDate(Date), content(String), picture(String), music(int), emo(String)} ")
    @GetMapping("/{diaryIdx}")
    // 일기 리턴
    public ResponseEntity<?> getDiary(@ApiParam(value = "일기 id")@PathVariable("diaryIdx") int diaryIdx){
        try{
            DiaryResponseDTO diaryResponseDTO=diaryService.getDiary(diaryIdx);
            if(diaryResponseDTO!=null) {
                responseDTO.put("responseMessage", ResponseMessage.DIARY_GET_SUCCESS);
                responseDTO.put("diary", diaryResponseDTO);
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            }else{
                responseDTO.put("responseMessage", ResponseMessage.DIARY_GET_FAIL);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
            }
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_GET_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "일기 내용 수정", notes =
            "일기 내용을 수정합니다.\n" +
                    "[Front] \n" +
                    "{diaryIdx(int), content(String), creationDate(Date), picture(String), music(int), emo(String)}\n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @PutMapping("")
    public ResponseEntity<?> updateDiary(@ApiParam(value = "일기 수정 정보")@RequestBody DiaryRequestDTO diary) {
        try{
            System.out.println("update: "+diary.getDiaryIdx());
            //diary keyword analyze
            //picture
            //music
            DiaryResponseDTO diaryResponseDTO=diaryService.updateDiary(diary.toEntity());
            responseDTO.put("responseMessage", ResponseMessage.DIARY_UPDATE_SUCCESS);
            responseDTO.put("diary", diaryResponseDTO);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_UPDATE_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "diaryIdx로 일기 삭제", notes =
            "일기를 삭제합니다.\n" +
                    "[Front] \n" +
                    "{diaryIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @DeleteMapping("/{diaryIdx}")
    public ResponseEntity<?> deleteDiary(@ApiParam(value="일기 id") @PathVariable("diaryIdx") int diaryIdx){
        try{
            diaryService.deleteDiary(diaryIdx);
            responseDTO.put("responseMessage", ResponseMessage.DIARY_DELETE_SUCCESS);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_DELETE_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "groupIdx로 일기 리스트 리턴", notes =
            "일기를 삭제합니다.\n" +
                    "[Front] \n" +
                    "{groupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "[{diaryIdx(int), userIdx(int), creationDate(Date), content(String), picture(String), music(int), emo(String)}]")
    @GetMapping("/list/{groupIdx}")
    public ResponseEntity<?> DiaryList(@PathVariable("groupIdx") int groupIdx){
        try{
            List<DiaryResponseDTO> diaryRequestDTOList=diaryService.diaryList(groupIdx);
            responseDTO.put("responseMessage", ResponseMessage.DIARY_LIST_FIND_SUCCESS);
            responseDTO.put("diary", diaryRequestDTOList);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_LIST_FIND_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }
}