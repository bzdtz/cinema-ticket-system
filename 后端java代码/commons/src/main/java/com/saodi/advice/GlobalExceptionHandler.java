package com.saodi.advice;

import com.saodi.vo.ResponseObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 兜底异常处理。此前接口抛异常时靠各自的 e.printStackTrace() 或干脆裸奔，
 * 客户端拿到的是 Spring 默认错误页，前端又统一 catch 掉，故障无声无息。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseObj<Void> handle(Exception e) {
        log.error("接口处理异常", e);
        // 不回传 e.getMessage()，避免把 SQL、表名、堆栈透给前端
        return ResponseObj.ERROR(500, "服务器内部错误");
    }
}
