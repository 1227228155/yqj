package com.erze.yqj.entity;

/**
 * Created by 1227228155@qq.com on 2017/6/26.
 */

public class RegisterPersonalDataBean {


    /**
     * code : 200
     * result : {"user_id":"27","user_nickname":"昵称","user_mobile":"18053104720","user_id_number":"383453","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/2017-06-27/5951d4bf55e8c.jpg","user_sex":"","user_birth":"1992-10-05","user_label":"哈哈哈,扯淡","user_inv_code":"6NT4HM","user_scole":0,"user_vip_status":"n"}
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
         * user_id : 27
         * user_nickname : 昵称
         * user_mobile : 18053104720
         * user_id_number : 383453
         * user_pic : https://yiapp.yiqijun.com/Uploads/UserPic/2017-06-27/5951d4bf55e8c.jpg
         * user_sex :
         * user_birth : 1992-10-05
         * user_label : 哈哈哈,扯淡
         * user_inv_code : 6NT4HM
         * user_scole : 0
         * user_vip_status : n
         */

        private String user_id;
        private String user_nickname;
        private String user_mobile;
        private String user_id_number;
        private String user_pic;
        private String user_sex;
        private String user_birth;
        private String user_label;
        private String user_inv_code;
        private int user_scole;
        private String user_vip_status;

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

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getUser_id_number() {
            return user_id_number;
        }

        public void setUser_id_number(String user_id_number) {
            this.user_id_number = user_id_number;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }

        public String getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(String user_sex) {
            this.user_sex = user_sex;
        }

        public String getUser_birth() {
            return user_birth;
        }

        public void setUser_birth(String user_birth) {
            this.user_birth = user_birth;
        }

        public String getUser_label() {
            return user_label;
        }

        public void setUser_label(String user_label) {
            this.user_label = user_label;
        }

        public String getUser_inv_code() {
            return user_inv_code;
        }

        public void setUser_inv_code(String user_inv_code) {
            this.user_inv_code = user_inv_code;
        }

        public int getUser_scole() {
            return user_scole;
        }

        public void setUser_scole(int user_scole) {
            this.user_scole = user_scole;
        }

        public String getUser_vip_status() {
            return user_vip_status;
        }

        public void setUser_vip_status(String user_vip_status) {
            this.user_vip_status = user_vip_status;
        }
    }
}
