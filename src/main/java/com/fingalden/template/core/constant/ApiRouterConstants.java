package com.fingalden.template.core.constant;

/**
 * @Author YouZhi
 * @Date 2025 - 01 - 02 - 12:57
 */
public class ApiRouterConstants {

    private ApiRouterConstants() {
        throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
    }

    /**
     * api 请求前缀
     */
    public static final String API_URL_PREFIX = "/api";

    /**
     * 用户模块 请求前缀
     */
    public static final String USER_URL_PREFIX = "/user";

    /**
     * 文章模块 请求前缀
     */
    public static final String OPUS_URL_PREFIX = "/opus";

    public static final String COMMENT_URL_PREFIX = "/comment";

    /**
     * 前台模块 请求前缀
     */
    public static final String API_FRONT_URL_PREFIX = API_URL_PREFIX + "/front";

    /**
     * 后台模块 请求前缀
     */
    public static final String API_BACK_URL_PREFIX = API_URL_PREFIX + "/back";

    /**
     * 前台用户 请求前缀
     */
    public static final String API_FRONT_USER_URL_PREFIX = API_FRONT_URL_PREFIX + USER_URL_PREFIX;

    /**
     * 后台用户 请求前缀
     */
    public static final String API_BACK_USER_URL_PREFIX = API_BACK_URL_PREFIX + USER_URL_PREFIX;

    /**
     * 后台用户 请求opus前缀
     */
    public static final String API_BACK_OPUS_URL_PREFIX = API_BACK_URL_PREFIX + OPUS_URL_PREFIX;

    /**
     * 前台用户 请求opus
     */
    public static final String API_FRONT_OPUS_URL_PREFIX = API_FRONT_URL_PREFIX + OPUS_URL_PREFIX;

    public static final String API_FRONT_COMMENT_URL_PREFIX = API_FRONT_URL_PREFIX + COMMENT_URL_PREFIX;

    /**
     * 资源（图片/视频/文档）模块请求路径前缀
     */
    public static final String RESOURCE_URL_PREFIX = "/resource";

    /**
     * 前台门户资源（图片/视频/文档）相关API请求路径前缀
     */
    public static final String API_FRONT_RESOURCE_URL_PREFIX = API_FRONT_URL_PREFIX + RESOURCE_URL_PREFIX;


}
