package com.erze.yqj.entity;

/**
 * Created by 1227228155@qq.com on 2017/8/10.
 */

public class ThirdReply {

    /**
     * code : 200
     * result : {"sreply_result":"二级回复内容","notice":true,"sreply_id":"6"}
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
         * sreply_result : 二级回复内容
         * notice : true
         * sreply_id : 6
         */

        private String sreply_result;
        private boolean notice;
        private String sreply_id;

        public String getSreply_result() {
            return sreply_result;
        }

        public void setSreply_result(String sreply_result) {
            this.sreply_result = sreply_result;
        }

        public boolean isNotice() {
            return notice;
        }

        public void setNotice(boolean notice) {
            this.notice = notice;
        }

        public String getSreply_id() {
            return sreply_id;
        }

        public void setSreply_id(String sreply_id) {
            this.sreply_id = sreply_id;
        }
    }
}
