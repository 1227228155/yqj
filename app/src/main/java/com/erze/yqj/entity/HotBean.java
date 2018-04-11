package com.erze.yqj.entity;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/9/7.
 */

public class HotBean {

    /**
     * code : 200
     * result : [{"user_id":"2","user_nickname":"用户2的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/5c7d610aed07f17c54e15ade5e976de7328d7b2e24d17-zd0OCr_fw658.jpg","user_id_number":"22222"},{"user_id":"1","user_nickname":"用户1的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/tou.png","user_id_number":"1111111"},{"user_id":"3","user_nickname":"用户3的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/062e0c0be6e092440182549bdee3000b662bf8bd2668c-UT9r8N_fw658.jpg","user_id_number":"33333"}]
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
         * user_id : 2
         * user_nickname : 用户2的名字
         * user_pic : https://yiapp.yiqijun.com/Uploads/UserPic/5c7d610aed07f17c54e15ade5e976de7328d7b2e24d17-zd0OCr_fw658.jpg
         * user_id_number : 22222
         */

        private String user_id;
        private String user_nickname;
        private String user_pic;
        private String user_id_number;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getUser_id_number() {
            return user_id_number;
        }

        public void setUser_id_number(String user_id_number) {
            this.user_id_number = user_id_number;
        }
    }
}
