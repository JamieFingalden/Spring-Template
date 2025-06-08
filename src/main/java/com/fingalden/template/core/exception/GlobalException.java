package com.fingalden.template.core.exception;

import com.fingalden.template.core.utils.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MyException.class)
    public RespBean handle(MyException e) {
        return RespBean.error().message("自定义异常" + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public RespBean handle2(RuntimeException e) {
        return RespBean.error().message("运行时异常" + e.getMessage());
    }

    @ExceptionHandler(ArithmeticException.class)
    public RespBean handle3(ArithmeticException e) {
        return RespBean.error().message("逻辑异常" + e.getMessage());
    }
}
