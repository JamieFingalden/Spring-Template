package com.fingalden.template.common.exception;

/**
 * 自定义异常类
 * 继承RuntimeException，用于系统中各种自定义异常场景
 */
public class MyException extends RuntimeException {
    /**
     * 无参构造方法
     */
    public MyException() {
        super();
    }

    /**
     * 带消息的构造方法
     *
     * @param message 异常消息
     */
    public MyException(String message) {
        super(message);
    }

    /**
     * 带消息和原因的构造方法
     *
     * @param message 异常消息
     * @param cause 异常原因
     */
    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 带原因的构造方法
     *
     * @param cause 异常原因
     */
    public MyException(Throwable cause) {
        super(cause);
    }

    /**
     * 全参构造方法
     *
     * @param message 异常消息
     * @param cause 异常原因
     * @param enableSuppression 是否启用抑制
     * @param writableStackTrace 是否可写堆栈跟踪
     */
    protected MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}