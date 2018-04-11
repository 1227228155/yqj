package com.erze.yqj.entity;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/8/1.
 */

public class ReplyBean {


    /**
     * code : 200
     * result : [{"reply_id":"2","user_id":"12","reply_contents":"5YWx5Lqn","reply_t":"2017-07-27 14:57:18","reply_type":"1","reply_user_nickname":"网上签约"},{"reply_id":"3","user_id":"11","reply_contents":"5YWx5Lqn","reply_t":"2017-07-27 15:00:01","reply_type":"1","reply_user_nickname":"热朱古力"},{"reply_id":"6","user_id":"10","b_reply_id":"12","reply_contents":"5LqM57qn5Zue5aSN5YaF5a65","reply_t":"2017-07-28 16:18:09","reply_type":"2","user_nickname":"千与千寻","b_user_nickname":"网上签约"},{"reply_id":"7","user_id":"20","b_reply_id":"11","reply_contents":"sssdfsd","reply_t":"2017-07-24 00:00:00","reply_type":"2","user_nickname":"nxnx","b_user_nickname":"热朱古力"},{"reply_id":"8","user_id":"21","b_reply_id":"11","reply_contents":"","reply_t":"0000-00-00 00:00:00","reply_type":"2","user_nickname":"xnxn","b_user_nickname":"热朱古力"},{"reply_id":"9","user_id":"12","reply_contents":"5YWx5Lqn","reply_t":"2017-07-27 14:49:06","reply_type":"1","reply_user_nickname":"网上签约"}]
     * msg : 成功
     */

    private String code;
    private String msg;
    private List<ResultBean> result;

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
        /**
         * reply_id : 2
         * user_id : 12
         * reply_contents : 5YWx5Lqn
         * reply_t : 2017-07-27 14:57:18
         * reply_type : 1
         * reply_user_nickname : 网上签约
         * b_reply_id : 12
         * user_nickname : 千与千寻
         * b_user_nickname : 网上签约
         */

        private String reply_id;
        private String user_id;
        private String reply_contents;
        private String reply_t;
        private String reply_type;
        private String reply_user_nickname;
        private String b_reply_id;
        private String user_nickname;
        private String b_user_nickname;

        public String getReply_id() {
            return reply_id;
        }

        public void setReply_id(String reply_id) {
            this.reply_id = reply_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getReply_contents() {
            return reply_contents;
        }

        public void setReply_contents(String reply_contents) {
            this.reply_contents = reply_contents;
        }

        public String getReply_t() {
            return reply_t;
        }

        public void setReply_t(String reply_t) {
            this.reply_t = reply_t;
        }

        public String getReply_type() {
            return reply_type;
        }

        public void setReply_type(String reply_type) {
            this.reply_type = reply_type;
        }

        public String getReply_user_nickname() {
            return reply_user_nickname;
        }

        public void setReply_user_nickname(String reply_user_nickname) {
            this.reply_user_nickname = reply_user_nickname;
        }

        public String getB_reply_id() {
            return b_reply_id;
        }

        public void setB_reply_id(String b_reply_id) {
            this.b_reply_id = b_reply_id;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getB_user_nickname() {
            return b_user_nickname;
        }

        public void setB_user_nickname(String b_user_nickname) {
            this.b_user_nickname = b_user_nickname;
        }
    }
}
