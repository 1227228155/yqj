package com.erze.yqj.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/9/7.
 */

public class BgBean implements Serializable{

    /**
     * code : 200
     * result : [{"url":"www.baiu.com/jfd.jpg"}]
     * msg : 成功
     */

    private String code;
    private String msg;
    private List<ResultBean> result;

    @Override
    public String toString() {
        return "BgBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        @Override
        public String toString() {
            return "ResultBean{" +
                    "url='" + url + '\'' +
                    '}';
        }

        /**
         * url : www.baiu.com/jfd.jpg
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
