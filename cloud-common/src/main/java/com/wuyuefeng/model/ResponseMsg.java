package com.wuyuefeng.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wuyuefeng.config.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMsg<T> {

    private String status;

    private Integer code;

    private T data;

    public static <T> JSONObject success(T data) {
        return generalResult("success", ResponseCode.SUCCESS, data);
    }

    public static JSONObject fail(){
        return generalResult("fail", ResponseCode.NOT_RESOURCE, "Internal Error");
    }

    private static <T> JSONObject generalResult(String status, Integer code, T data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("code", code);
        jsonObject.put("data", data);
        return jsonObject;
    }
}
