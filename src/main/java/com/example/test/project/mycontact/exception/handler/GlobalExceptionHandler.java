package com.example.test.project.mycontact.exception.handler;

import com.example.test.project.mycontact.exception.PersonNotFoundException;
import com.example.test.project.mycontact.exception.RenameIsNotPermittedException;
import com.example.test.project.mycontact.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice // RestController 전체에 Exception 처리를 해줌
public class GlobalExceptionHandler {
    @ExceptionHandler(RenameIsNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 여기는 상태를 알려주는 신호 코드
    public ErrorResponse handleRenameNoPermittedException(RenameIsNotPermittedException ex, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage()); // error response의 인자로 주어지는 bad_request
    }

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePersonNotFoundException(PersonNotFoundException ex, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException ex, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        //보안상의 이유로 ex.getMessage() 생략
        log.error("서버오류 : {}", ex.getMessage(), ex);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 오류가 발생하였습니다.");
    }
}
