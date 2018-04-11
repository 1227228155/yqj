package com.erze.yqj.entity;

/**
 * Created by 1227228155@qq.com on 2017/8/3.
 */

public class SendComment {

    /**
     * code : 200
     * result : {"comm_result":"评论内容","notice":true,"comm_id":"10"}
     * msg : 成功
     */

    private String code;
    private ResultBean result;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResultBean {
        /**
         * comm_result : 评论内容
         * notice : true
         * comm_id : 10
         */

        private String comm_result;
        private boolean notice;
        private String comm_id;

        public String getComm_result() {
            return comm_result;
        }

        public void setComm_result(String comm_result) {
            this.comm_result = comm_result;
        }

        public boolean isNotice() {
            return notice;
        }

        public void setNotice(boolean notice) {
            this.notice = notice;
        }

        public String getComm_id() {
            return comm_id;
        }

        public void setComm_id(String comm_id) {
            this.comm_id = comm_id;
        }
    }
}
