package com.ly.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
  * @Description: 返回数据
  *
  * @author MING
  * @eamil lmm_work@163.com
  * @date 2018/11/20 10:28
  */
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public Result() {
        put("code" , 0);
        put("msg" , "success");
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.put("code" , code);
        result.put("msg" , msg);
        return result;
    }

    public static Result ok(String msg) {
        Result result = new Result();
        result.put("msg" , msg);
        return result;
    }

    public static Result ok(Map<String, Object> map) {
        Result result = new Result();
        result.putAll(map);
        return result;
    }

    public static Result ok() {
        return new Result();
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
