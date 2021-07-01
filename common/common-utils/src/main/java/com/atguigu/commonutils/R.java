package com.atguigu.commonutils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author li
 * @create 2021-04-23 14:12
 */

@Data
public class R {
    private Boolean success;
    private  Integer code;
    private String message;
    private Map<String,Object> data=new HashMap<>();

    private R(){}


    public static R ok(){
        R r = new R();

        r.setSuccess(true);
        r.setCode(com.atguigu.commonutils.ResultCode.SUCCESS);
        r.setMessage("操作成功");
        return r;
    }

    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(com.atguigu.commonutils.ResultCode.Error);
        r.setMessage("操作失败");
        return r;
    }



    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
