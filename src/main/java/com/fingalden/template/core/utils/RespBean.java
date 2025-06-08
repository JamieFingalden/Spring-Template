package com.fingalden.template.core.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RespBean {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();

    public static RespBean ok() {
        RespBean r = new RespBean();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }

    public static RespBean error() {
        RespBean r = new RespBean();
        r.setSuccess(false);
        r.setCode(201);
        r.setMessage("失败");
        return r;
    }

    public RespBean success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public RespBean message(String message) {
        this.setMessage(message);
        return this;
    }

    public RespBean code(Integer code) {
        this.setCode(code);
        return this;
    }

    public RespBean data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public RespBean data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}