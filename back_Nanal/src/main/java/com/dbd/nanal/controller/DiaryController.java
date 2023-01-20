package com.dbd.nanal.controller;

import com.dbd.nanal.dto.DiaryRequestDTO;
import com.dbd.nanal.dto.DiaryResponseDTO;
import com.dbd.nanal.service.DiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Api(tags = {"Diary"})
@RestController
@RequiredArgsConstructor
@RequestMapping("diary")
public class DiaryController {

    private final DiaryService diaryService;

    @ApiOperation(value = "일기 생성", notes = "새로운 일기를 생성합니다.")
    @PostMapping("")
    public ResponseEntity<?> writeDiary(@ApiParam(value = "일기 정보")@RequestBody DiaryRequestDTO diary, HttpSession session) throws Exception{
        try{
            //diary keyword analyze
            //picture
            //music
            return new ResponseEntity<>(diaryService.save(diary.toEntity()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "일기 반환", notes = "일기를 반환합니다.")
    @GetMapping("/{diaryIdx}")
    public ResponseEntity<?> getDiary(@ApiParam(value = "그룹 id")@PathVariable("diaryIdx") int diaryIdx)throws  Exception{
        try{
            DiaryResponseDTO dto=diaryService.getDiary(diaryIdx);
            if(dto!=null){
                return new ResponseEntity<DiaryResponseDTO>(dto, HttpStatus.OK);
            }else{
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }

        }catch (Exception e){
            return new ResponseEntity<String>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @ApiOperation(value = "일기 리스트 반환", notes = "일기 리스트를 반환합니다.")
//    @GetMapping("/{groupId}")
//    public ResponseEntity<?> DiaryList(@PathVariable("groupId") int groupId)throws  Exception{
//        try{
//            return new ResponseEntity<List<DiaryResponseDTO>>(diaryService.diaryList(groupId));
//        }catch (Exception e){
//            return new ResponseEntity<String>("서버오류", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
