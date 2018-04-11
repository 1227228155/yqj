package com.erze.yqj.entity;

/**
 * Created by 1227228155@qq.com on 2017/7/28.
 */

public class Code {


    /**
     * code : 200
     * result : {"upvote_comm_status":"false 及时通知状态"}
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
         * upvote_comm_status : false 及时通知状态
         */

        private String upvote_comm_status;

        public String getUpvote_comm_status() {
            return upvote_comm_status;
        }

        public void setUpvote_comm_status(String upvote_comm_status) {
            this.upvote_comm_status = upvote_comm_status;
        }
    }
}
