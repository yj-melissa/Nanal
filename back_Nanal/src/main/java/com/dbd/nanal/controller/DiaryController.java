package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.*;
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

    @ApiOperation(value = "일기 생성", notes =
            "일기를 생성합니다.\n" +
                    "[Front] \n" +
                    "{userIdx(int), creationDate(Date), groupIdx(List<Integer>)} \n\n" +
                    "[Back] \n" +
                    "{diaryIdx(int), userIdx(int), creationDate(Date), content(String), picture(String), music(int), emo(String)} ")
    @PostMapping("")
    public ResponseEntity<?> writeDiary(@ApiParam(value = "일기 정보")@RequestBody DiaryRequestDTO diary) {
        HashMap<String, Object> responseDTO = new HashMap<>();
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
        HashMap<String, Object> responseDTO = new HashMap<>();
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
        HashMap<String, Object> responseDTO = new HashMap<>();
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
        HashMap<String, Object> responseDTO = new HashMap<>();
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
        HashMap<String, Object> responseDTO = new HashMap<>();
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

    @ApiOperation(value = "일기 댓글 작성", notes =
            "일기 댓글을 작성합니다.\n" +
                    "[Front] \n" +
                    "{diaryIdx(int), userIdx(int), groupIdx(int), content(String)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @PostMapping("/comment")
    public ResponseEntity<?> writeComment(@ApiParam(value = "댓글 정보") @RequestBody DiaryCommentRequestDTO diaryCommentRequestDTO) {
        HashMap<String, Object> responseDTO = new HashMap<>();
        DiaryCommentResponseDTO diaryCommentResponseDTO=diaryService.saveComment(diaryCommentRequestDTO);
        responseDTO.put("responseMessage", ResponseMessage.DIARY_COMMENT_SAVE_SUCCESS);
        responseDTO.put("diaryComment", diaryCommentResponseDTO);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK); // temp
    }

    @ApiOperation(value = "일기 댓글 수정", notes =
            "일기 댓글을 수정합니다.\n" +
                    "[Front] \n" +
                    "{commentIdx(int), content(String)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @PutMapping("/diary/comment")
    public ResponseEntity<?> updateComment(@ApiParam(value="댓글 수정 정보") @RequestBody DiaryCommentRequestDTO diaryCommentRequestDTO){
        HashMap<String, Object> responseDTO = new HashMap<>();
        DiaryCommentResponseDTO diaryCommentResponseDTO=diaryService.updateComment(diaryCommentRequestDTO);
        responseDTO.put("responseMessage", ResponseMessage.DIARY_COMMENT_SAVE_FAIL);
        responseDTO.put("diaryComment", diaryCommentResponseDTO);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK); // temp
    }

    @ApiOperation(value = "일기 댓글 리스트 리턴", notes =
            "일기에 해당하는 댓글 리스트를 반환합니다..\n" +
                    "[Front] \n" +
                    "{diaryIdx(int), groupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @GetMapping("/diary/comment/{groupIdx}/{diaryIdx}")
    public ResponseEntity<?> getCommentList(@ApiParam(value="댓글 리스트 조회 정보")@PathVariable("diaryIdx") int diaryIdx,@PathVariable("groupIdx") int groupIdx){
        HashMap<String, Object> responseDTO = new HashMap<>();
        List<DiaryCommentResponseDTO> diaryCommentList=diaryService.getDiaryCommentList(groupIdx, diaryIdx);
        responseDTO.put("responseMessage", ResponseMessage.DIARY_COMMEMT_LIST_FIND_SUCCESS);
        responseDTO.put("diaryComment", diaryCommentList);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "commentIdx로 일기 삭제", notes =
            "일기를 삭제합니다.\n" +
                    "[Front] \n" +
                    "{commentIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @DeleteMapping("/comment/{commentIdx}")
    public ResponseEntity<?> deleteDiaryComment(@ApiParam(value="댓글 id") @PathVariable("commentIdx") int commentIdx){
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            diaryService.deleteDiaryComment(commentIdx);
            responseDTO.put("responseMessage", ResponseMessage.DIARY_COMMENT_DELETE_SUCCESS);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_COMMENT_DELETE_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }
}