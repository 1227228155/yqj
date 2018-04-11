package com.erze.yqj.entity;

/**
 * Created by 1227228155@qq.com on 2017/8/4.
 */

public class SendReply {

    /**
     * code : 200
     * result : {"reply_result":"回复内容","notice":true,"reply_id":"4"}
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
         * reply_result : 回复内容
         * notice : true
         * reply_id : 4
         */

        private String reply_result;
        private boolean notice;
        private String reply_id;

        public String getReply_result() {
            return reply_result;
        }

        public void setReply_result(String reply_result) {
            this.reply_result = reply_result;
        }

        public boolean isNotice() {
            return notice;
        }

        public void setNotice(boolean notice) {
            this.notice = notice;
        }

        public String getReply_id() {
            return reply_id;
        }

        public void setReply_id(String reply_id) {
            this.reply_id = reply_id;
        }
    }
}
