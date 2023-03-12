package com.kanojo.exception;

import com.kanojo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 外键异常
 */

@Slf4j
@Order(1)
@RestControllerAdvice
public class MyExceptionAdvice {

    @ExceptionHandler(MyException.class)
    public Result doMyException(MyException e) {
        log.error(e.getMessage());
        return Result.failed(e.getMessage());
    }
}
