package com.dbd.nanal.config;


import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@RestControllerAdvice // 모든 @Controller. 즉, 전역에서 발생할 수 있는 예외를 잡아 처리해 주는 annotation
@Slf4j // 해당 어노테이션이 선언된 클래스에 자동으로 로그 객체 생성. log.debug()와 같이 로깅 관련 메서드 사용
public class GlobalExceptionHandler {

    HashMap<String, Object> responseDTO = new HashMap<>();

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(Exception e) {
        System.out.println("RuntimeException");
        log.error("handleRuntimeException : {}", e.getMessage());
        responseDTO.put("responseMessage", ResponseMessage.RUNTIME);
        return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e){
        log.error("handleNullPointerException : {}", e.getMessage());
        responseDTO.put("responseMessage", ResponseMessage.EMPTY);
        return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
    }

//    @ExceptionHandler(NoD)

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKeyException(String key) {
        log.error("handleDuplicateKeyException : {}", key);
        responseDTO.put("responseMessage", ResponseMessage.DUPLICATED_VALUE);
        return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
    }




}
