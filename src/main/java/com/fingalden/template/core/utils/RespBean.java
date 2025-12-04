package com.fingalden.template.core.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应格式工具类
 * 用于封装API响应，包含成功状态、状态码、消息和数据
 */
@Data
public class RespBean {
    /**
     * 响应状态：true-成功，false-失败
     */
    private Boolean success;

    /**
     * 响应状态码：
     * 200-成功
     * 400-请求错误
     * 401-未授权
     * 403-禁止访问
     * 500-服务器内部错误
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private Map<String, Object> data = new HashMap<String, Object>();

    /**
     * 私有构造方法，防止直接创建实例
     */
    private RespBean() {
    }

    /**
     * 创建成功响应
     *
     * @return RespBean对象，success=true，code=200，message="成功"
     */
    public static RespBean success() {
        RespBean r = new RespBean();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }

    /**
     * 创建带消息的成功响应
     *
     * @param message 响应消息
     * @return RespBean对象，success=true，code=200
     */
    public static RespBean success(String message) {
        RespBean r = new RespBean();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage(message);
        return r;
    }

    /**
     * 创建带数据的成功响应
     *
     * @param key   数据键名
     * @param value 数据值
     * @return RespBean对象，success=true，code=200，message="成功"
     */
    public static RespBean success(String key, Object value) {
        RespBean r = new RespBean();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("成功");
        r.getData().put(key, value);
        return r;
    }

    /**
     * 创建带消息和数据的成功响应
     *
     * @param message 响应消息
     * @param key     数据键名
     * @param value   数据值
     * @return RespBean对象，success=true，code=200
     */
    public static RespBean success(String message, String key, Object value) {
        RespBean r = new RespBean();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage(message);
        r.getData().put(key, value);
        return r;
    }

    /**
     * 创建错误响应
     *
     * @return RespBean对象，success=false，code=500，message="失败"
     */
    public static RespBean error() {
        RespBean r = new RespBean();
        r.setSuccess(false);
        r.setCode(500);
        r.setMessage("失败");
        return r;
    }

    /**
     * 创建带消息的错误响应
     *
     * @param message 响应消息
     * @return RespBean对象，success=false，code=500
     */
    public static RespBean error(String message) {
        RespBean r = new RespBean();
        r.setSuccess(false);
        r.setCode(500);
        r.setMessage(message);
        return r;
    }

    /**
     * 创建带状态码和消息的错误响应
     *
     * @param code    响应状态码
     * @param message 响应消息
     * @return RespBean对象，success=false
     */
    public static RespBean error(Integer code, String message) {
        RespBean r = new RespBean();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    /**
     * 设置响应状态
     *
     * @param success 响应状态：true-成功，false-失败
     * @return 当前RespBean对象
     */
    public RespBean success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    /**
     * 设置响应消息
     *
     * @param message 响应消息
     * @return 当前RespBean对象
     */
    public RespBean message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 设置响应状态码
     *
     * @param code 响应状态码
     * @return 当前RespBean对象
     */
    public RespBean code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 添加单个数据项
     *
     * @param key   数据键名
     * @param value 数据值
     * @return 当前RespBean对象
     */
    public RespBean data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 设置整个数据Map
     *
     * @param map 数据Map
     * @return 当前RespBean对象
     */
    public RespBean data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    /**
     * 设置单个数据对象，键名为"data"
     * 方便直接返回单个数据对象
     *
     * @param value 数据值
     * @return 当前RespBean对象
     */
    public RespBean data(Object value) {
        this.data.put("data", value);
        return this;
    }

    /**
     * 添加分页数据
     *
     * @param records 数据列表
     * @param total   总记录数
     * @param current 当前页码
     * @param size    每页大小
     * @return 当前RespBean对象
     */
    public RespBean pageData(Object records, long total, int current, int size) {
        this.data.put("records", records);
        this.data.put("total", total);
        this.data.put("current", current);
        this.data.put("size", size);
        this.data.put("pages", (total + size - 1) / size); // 计算总页数
        return this;
    }
}