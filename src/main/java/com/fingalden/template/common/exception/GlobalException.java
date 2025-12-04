package com.fingalden.template.common.exception;

import com.fingalden.template.core.utils.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器，用于处理系统中所有的异常
 * 使用@RestControllerAdvice注解，使其能够处理所有控制器中的异常
 * 并返回JSON格式的响应
 */
@RestControllerAdvice
public class GlobalException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    /**
     * 处理自定义异常MyException
     *
     * @param e 自定义异常对象
     * @return 统一响应格式的RespBean对象
     */
    @ExceptionHandler(MyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean handleMyException(MyException e) {
        logger.error("自定义异常: {}", e.getMessage(), e);
        return RespBean.error().code(500).message(e.getMessage());
    }

    /**
     * 处理Spring Security认证异常
     * 当用户认证失败时会抛出此类异常
     *
     * @param e 认证异常对象
     * @return 统一响应格式的RespBean对象
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespBean handleAuthenticationException(AuthenticationException e) {
        logger.error("认证异常: {}", e.getMessage(), e);
        return RespBean.error().code(401).message("认证失败: " + e.getMessage());
    }

    /**
     * 处理Spring Security访问拒绝异常
     * 当用户没有权限访问某个资源时会抛出此类异常
     *
     * @param e 访问拒绝异常对象
     * @return 统一响应格式的RespBean对象
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RespBean handleAccessDeniedException(AccessDeniedException e) {
        logger.error("访问拒绝异常: {}", e.getMessage(), e);
        return RespBean.error().code(403).message("访问被拒绝: " + e.getMessage());
    }

    /**
     * 处理方法参数验证异常
     * 当使用@Validated或@Valid注解验证方法参数失败时会抛出此类异常
     *
     * @param e 方法参数验证异常对象
     * @return 统一响应格式的RespBean对象，包含具体的验证错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBean handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("方法参数验证异常: {}", e.getMessage(), e);
        
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        
        // 遍历所有验证错误，将字段名和错误信息放入map中
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        
        return RespBean.error().code(400).message("参数验证失败").data("errors", errors);
    }

    /**
     * 处理绑定异常
     * 当使用@ModelAttribute注解绑定参数失败时会抛出此类异常
     *
     * @param e 绑定异常对象
     * @return 统一响应格式的RespBean对象，包含具体的绑定错误信息
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBean handleBindException(BindException e) {
        logger.error("绑定异常: {}", e.getMessage(), e);
        
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        
        // 遍历所有绑定错误，将字段名和错误信息放入map中
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        
        return RespBean.error().code(400).message("参数绑定失败").data("errors", errors);
    }

    /**
     * 处理方法参数类型不匹配异常
     * 当方法参数类型转换失败时会抛出此类异常
     *
     * @param e 方法参数类型不匹配异常对象
     * @return 统一响应格式的RespBean对象
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBean handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.error("方法参数类型不匹配异常: {}", e.getMessage(), e);
        return RespBean.error().code(400).message("参数类型不匹配: " + e.getMessage());
    }

    /**
     * 处理空指针异常
     *
     * @param e 空指针异常对象
     * @return 统一响应格式的RespBean对象
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean handleNullPointerException(NullPointerException e) {
        logger.error("空指针异常: {}", e.getMessage(), e);
        return RespBean.error().code(500).message("系统内部错误: 空指针异常");
    }

    /**
     * 处理IO异常
     *
     * @param e IO异常对象
     * @return 统一响应格式的RespBean对象
     */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean handleIOException(IOException e) {
        logger.error("IO异常: {}", e.getMessage(), e);
        return RespBean.error().code(500).message("系统内部错误: IO异常");
    }

    /**
     * 处理算术异常
     *
     * @param e 算术异常对象
     * @return 统一响应格式的RespBean对象
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean handleArithmeticException(ArithmeticException e) {
        logger.error("算术异常: {}", e.getMessage(), e);
        return RespBean.error().code(500).message("系统内部错误: 算术异常");
    }

    /**
     * 处理运行时异常
     * 作为兜底异常处理，处理所有未被其他异常处理器处理的运行时异常
     *
     * @param e 运行时异常对象
     * @return 统一响应格式的RespBean对象
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean handleRuntimeException(RuntimeException e) {
        logger.error("运行时异常: {}", e.getMessage(), e);
        return RespBean.error().code(500).message("系统内部错误: " + e.getMessage());
    }

    /**
     * 处理所有异常
     * 作为最终兜底异常处理，处理所有未被其他异常处理器处理的异常
     *
     * @param e 异常对象
     * @return 统一响应格式的RespBean对象
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean handleException(Exception e) {
        logger.error("系统异常: {}", e.getMessage(), e);
        return RespBean.error().code(500).message("系统内部错误");
    }
}