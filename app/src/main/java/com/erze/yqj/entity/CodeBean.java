package com.erze.yqj.entity;

/**
 * Created by 1227228155@qq.com on 2017/7/26.
 */

public class CodeBean {

    /**
     * code : 200
     * msg : 成功
     */

    private String code;
    private String msg;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "CodeBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
