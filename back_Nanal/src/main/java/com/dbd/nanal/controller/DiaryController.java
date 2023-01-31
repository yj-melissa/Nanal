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

import java.text.SimpleDateFormat;
import java.util.*;

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
            List<String> keywordList=new ArrayList<>();
            //picture
            //music

            //save diary
            DiaryResponseDTO diaryResponseDTO=diaryService.save(diary);
            int diaryIdx=diaryResponseDTO.getDiaryIdx();

            //save diary-group
            for(int i=0; i<diary.getGroupIdxList().size(); i++){
                GroupDiaryRelationDTO groupDiaryRelationDTO=new GroupDiaryRelationDTO(diaryIdx, diary.getGroupIdxList().get(i));
                diaryService.saveDiaryGroup(groupDiaryRelationDTO);
            }

            //save keyword
            diaryService.saveKeyword(diaryResponseDTO.getDiaryIdx(), keywordList);

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
                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            }
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_GET_FAIL);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
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
            //diary keyword analyze
            List<String> keywordList=new ArrayList<>();

            //picture
            //music
            DiaryResponseDTO diaryResponseDTO=diaryService.updateDiary(diary.toEntity());

            //save keyword
            diaryService.saveKeyword(diaryResponseDTO.getDiaryIdx(), keywordList);

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
            List<DiaryResponseDTO> diaryResponseDTOList=diaryService.diaryList(groupIdx);
            responseDTO.put("responseMessage", ResponseMessage.DIARY_LIST_FIND_SUCCESS);
            responseDTO.put("diary", diaryResponseDTOList);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_LIST_FIND_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "날짜로 일기 리스트 리턴", notes =
            "날짜로 일기 리스트를 리턴합니다.\n" +
                    "[Front] \n" +
                    "{date(yyyy-mm-dd)} \n\n" +
                    "[Back] \n" +
                    "[{diaryIdx(int), userIdx(int), creationDate(Date), content(String), picture(String), music(int), emo(String)}]")
    @GetMapping("/list/date/{date}")
    public ResponseEntity<?> DiaryList(@PathVariable("date") String date){
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            List<DiaryResponseDTO> diaryResponseDTOList;
            // yyyy-mm-00
            if((date.split("-")[2]).equals("00")) {
                String findDate=date.substring(0,7);
                diaryResponseDTOList = diaryService.getMonthDiaryList(findDate);
            }// yyyy-mm-dd
            else {
                SimpleDateFormat formatter=new SimpleDateFormat("yyyy-mm-dd");
                Date findDate=formatter.parse(date);
                diaryResponseDTOList = diaryService.getDateDiaryList(findDate);
            }
            responseDTO.put("responseMessage", ResponseMessage.DIARY_LIST_FIND_SUCCESS);
            responseDTO.put("diary", diaryResponseDTOList);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_LIST_FIND_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "userIdx로 전체 일기 리스트 리턴", notes =
            "일기를 삭제합니다.\n" +
                    "[Front] \n" +
                    "{userIdx(int)} \n\n" +
                    "[Back] \n" +
                    "[{diaryIdx(int), userIdx(int), creationDate(Date), content(String), picture(String), music(int), emo(String)}]")
    @GetMapping("/list/user/{userIdx}")
    public ResponseEntity<?> userDiaryList(@PathVariable("userIdx") int userIdx){
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            List<DiaryResponseDTO> diaryResponseDTOList=diaryService.userDiaryList(userIdx);
            responseDTO.put("responseMessage", ResponseMessage.DIARY_LIST_FIND_SUCCESS);
            responseDTO.put("diary", diaryResponseDTOList);
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
    @PutMapping("/comment")
    public ResponseEntity<?> updateComment(@ApiParam(value="댓글 수정 정보") @RequestBody DiaryCommentRequestDTO diaryCommentRequestDTO){
        HashMap<String, Object> responseDTO = new HashMap<>();
        DiaryCommentResponseDTO diaryCommentResponseDTO=diaryService.updateComment(diaryCommentRequestDTO);
        responseDTO.put("responseMessage", ResponseMessage.DIARY_COMMENT_SAVE_SUCCESS);
        responseDTO.put("diaryComment", diaryCommentResponseDTO);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK); // temp
    }

    @ApiOperation(value = "일기 댓글 리스트 리턴", notes =
            "일기에 해당하는 댓글 리스트를 반환합니다..\n" +
                    "[Front] \n" +
                    "{diaryIdx(int), groupIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @GetMapping("/comment/{groupIdx}/{diaryIdx}")
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

    // 휴지통
    @ApiOperation(value = "userIdx로 휴지통 목록 불러오기", notes =
            "사용자의 휴지통 목록을 리턴합니다.\n" +
                    "[Front] \n" +
                    "{userIdx(int)} \n\n" +
                    "[Back] \n" +
                    "[{diaryIdx(int), userIdx(int), creationDate(Date), content(String), picture(String), music(int), emo(String)}]")
    @GetMapping("/trashbin/{userIdx}")
    public ResponseEntity<?> getTrashBinDiaryList(@PathVariable("userIdx") int userIdx){
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            List<DiaryResponseDTO> diaryResponseDTOList=diaryService.getTrashDiary(userIdx);
            responseDTO.put("responseMessage", ResponseMessage.DIARY_LIST_FIND_SUCCESS);
            responseDTO.put("diary", diaryResponseDTOList);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_LIST_FIND_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "userIdx로 휴지통 비우기", notes =
            "휴지통을 비우기.\n" +
                    "[Front] \n" +
                    "{userIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @DeleteMapping("/trashbin/{userIdx}")
    public ResponseEntity<?> deleteTrashBin(@ApiParam(value="user id") @PathVariable("userIdx") int userIdx){
        HashMap<String, Object> responseDTO = new HashMap<>();
        try{
            diaryService.deleteTrashBin(userIdx);
            responseDTO.put("responseMessage", ResponseMessage.DIARY_DELETE_SUCCESS);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }catch (Exception e){
            responseDTO.put("responseMessage", ResponseMessage.DIARY_DELETE_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "휴지통에서 일기 복구", notes =
            "휴지통에서 일기를 복구합니다.\n" +
                    "[Front] \n" +
                    "{diaryIdx(int)} \n\n" +
                    "[Back] \n" +
                    "{diaryIdx(int), userIdx(int), creationDate(Date), content(String), picture(String), music(int), emo(String)}")
    @PutMapping("/trashbin/{diaryIdx}")
    public ResponseEntity<?> reDiary(@ApiParam(value="일기 번호") @PathVariable("diaryIdx") int diaryIdx){
        HashMap<String, Object> responseDTO = new HashMap<>();
        DiaryResponseDTO diaryResponseDTO=diaryService.reDiary(diaryIdx);
        responseDTO.put("responseMessage", ResponseMessage.DIARY_SAVE_SUCCESS);
        responseDTO.put("diaryComment", diaryResponseDTO);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK); // temp
    }

    //bookmark
    @ApiOperation(value = "일기 북마크 저장", notes =
            "일기 북마크 저장합니다.\n" +
                    "[Front] \n" +
                    "{diaryIdx(int), userIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @PostMapping("/bookmark")
    public ResponseEntity<?> saveBookmark(@ApiParam(value = "북마크 정보") @RequestBody BookmarkRequestDTO bookMarkRequestDTO) {
        HashMap<String, Object> responseDTO = new HashMap<>();
        diaryService.saveBookmark(bookMarkRequestDTO);
        responseDTO.put("responseMessage", ResponseMessage.DIARY_BOOKMARK_SAVE_SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK); // temp
    }

    @ApiOperation(value = "일기 북마크 리스트 불러오기", notes =
            "일기 북마크 리스트를 반환합니다.\n" +
                    "[Front] \n" +
                    "{userIdx(int)} \n\n" +
                    "[Back] \n" +
                    "[{bookmarkIdx(int), diaryIdx(int), userIdx(int), creationDate(Date), content(String), picture(String), music(int), emo(String)}]")
    @GetMapping("/bookmark/{userIdx}")
    public ResponseEntity<?> getBookmarkList(@ApiParam(value = "유저 정보")@PathVariable("userIdx") int userIdx) {
        HashMap<String, Object> responseDTO = new HashMap<>();
        List<BookmarkResponseDTO> bookmarkList= diaryService.getBookmarkList(userIdx);
        responseDTO.put("responseMessage", ResponseMessage.DIARY_BOOKMARK_LIST_SUCCESS);
        responseDTO.put("BookmarkList", bookmarkList);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK); // temp
    }

    @ApiOperation(value = "일기 북마크 삭제", notes =
            "일기 북마크를 삭제합니다.\n" +
                    "[Front] \n" +
                    "{bookmarkIdx(int)} \n\n" +
                    "[Back] \n" +
                    "ok(200)")
    @DeleteMapping("/bookmark/{bookmarkIdx}")
    public ResponseEntity<?> deleteBookmark(@ApiParam(value="일기 스크랩 id")@PathVariable("bookmarkIdx") int bookmarkIdx){
        HashMap<String, Object> responseDTO = new HashMap<>();
        diaryService.deleteBookmark(bookmarkIdx);
        responseDTO.put("responseMessage", ResponseMessage.DIARY_BOOKMARK_DELETE_SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }
}