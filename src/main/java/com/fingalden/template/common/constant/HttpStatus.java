package com.fingalden.template.common.constant;

/**
 * HTTP状态码常量类
 * 统一管理系统中使用的HTTP状态码和响应消息
 */
public class HttpStatus {

    /**
     * 操作成功
     */
    public static final int SUCCESS = 200;
    public static final String SUCCESS_MSG = "成功";

    /**
     * 请求错误
     */
    public static final int BAD_REQUEST = 400;
    public static final String BAD_REQUEST_MSG = "请求错误";

    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;
    public static final String UNAUTHORIZED_MSG = "未授权";

    /**
     * 禁止访问
     */
    public static final int FORBIDDEN = 403;
    public static final String FORBIDDEN_MSG = "禁止访问";

    /**
     * 资源不存在
     */
    public static final int NOT_FOUND = 404;
    public static final String NOT_FOUND_MSG = "资源不存在";

    /**
     * 服务器内部错误
     */
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final String INTERNAL_SERVER_ERROR_MSG = "服务器内部错误";

    /**
     * 服务不可用
     */
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final String SERVICE_UNAVAILABLE_MSG = "服务不可用";

    /**
     * 网络超时
     */
    public static final int GATEWAY_TIMEOUT = 504;
    public static final String GATEWAY_TIMEOUT_MSG = "网络超时";

    /**
     * 参数错误
     */
    public static final int PARAM_ERROR = 400;
    public static final String PARAM_ERROR_MSG = "参数错误";

    /**
     * 数据重复
     */
    public static final int DATA_DUPLICATE = 400;
    public static final String DATA_DUPLICATE_MSG = "数据重复";

    /**
     * 数据不存在
     */
    public static final int DATA_NOT_FOUND = 404;
    public static final String DATA_NOT_FOUND_MSG = "数据不存在";

    /**
     * 业务逻辑错误
     */
    public static final int BUSINESS_ERROR = 400;
    public static final String BUSINESS_ERROR_MSG = "业务逻辑错误";
}