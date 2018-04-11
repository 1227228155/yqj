package com.erze.yqj.api;

/**
 * Created by 1227228155@qq.com on 2017/9/5.
 */

public class NetResponse {
    /**网络访问结果*/
    public String result;
    /**网络错误原因*/
    public String data;
    /**token*/
    public int bool;

    @Override
    public String toString() {
        return "NetResponse{" +
                "result='" + result + '\'' +
                ", data='" + data + '\'' +
                ", bool='" + bool + '\'' +
                '}';
    }
}
