package io.app.exceptions;

import io.app.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse handleResourceNotFoundException(ResourceNotFoundException ex){
        log.error(ex.getMessage()+"-"+ex.getCause()+"-{}"+new Date());
        return ApiResponse.builder()
                .msg(ex.getMessage())
                .status(false)
                .build();
    }
}
