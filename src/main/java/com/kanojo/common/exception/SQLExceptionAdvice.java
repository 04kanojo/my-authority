package com.kanojo.common.exception;

import com.kanojo.common.result.Result;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@Order(1)
@RestControllerAdvice
public class SQLExceptionAdvice {

    @ExceptionHandler(SQLException.class)
    public Result doException(SQLException e) {
        e.printStackTrace();
        return Result.failed("sql异常");
    }
}
