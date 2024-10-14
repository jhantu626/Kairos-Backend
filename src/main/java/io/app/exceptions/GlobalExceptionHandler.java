package io.app.exceptions;

import io.app.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleResourceNotFoundException(ResourceNotFoundException ex){
        log.error(ex.getMessage()+"-"+ex.getCause()+"-{}"+new Date());
        return ApiResponse.builder()
                .msg(ex.getMessage())
                .status(false)
                .build();
    }


    @ExceptionHandler(DuplicateFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse handleDuplicateFOundExxeption(DuplicateFoundException ex){
        log.error(ex.getMessage()+"-"+ex.getCause()+" at [{}]"+new Date());
        return ApiResponse.builder()
                .msg(ex.getMessage())
                .status(false)
                .build();
    }

}
