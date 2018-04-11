package com.erze.yqj.entity;

/**
 * Created by 1227228155@qq.com on 2017/6/24.
 */

public class RegisterBean {

    /**
     * code : 200
     * result : {"user_id":"1","user_id_number":"0","user_mobile":"13845255539","user_sex":"","user_nickname":"","user_pic":"","user_age":"0","user_birth":"0000-00-00","user_signature":"","user_school":"","user_label":"","user_character":"","user_qq":"","user_back_wall":"","user_inv_code":"","use_scole":"0","user_vip_status":"n","user_status":"y"}
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
        @Override
        public String toString() {
            return "ResultBean{" +
                    "user_id='" + user_id + '\'' +
                    ", user_id_number='" + user_id_number + '\'' +
                    ", user_mobile='" + user_mobile + '\'' +
                    ", user_sex='" + user_sex + '\'' +
                    ", user_nickname='" + user_nickname + '\'' +
                    ", user_pic='" + user_pic + '\'' +
                    ", user_age='" + user_age + '\'' +
                    ", user_birth='" + user_birth + '\'' +
                    ", user_signature='" + user_signature + '\'' +
                    ", user_school='" + user_school + '\'' +
                    ", user_label='" + user_label + '\'' +
                    ", user_character='" + user_character + '\'' +
                    ", user_qq='" + user_qq + '\'' +
                    ", user_back_wall='" + user_back_wall + '\'' +
                    ", user_inv_code='" + user_inv_code + '\'' +
                    ", use_scole='" + use_scole + '\'' +
                    ", user_vip_status='" + user_vip_status + '\'' +
                    ", user_status='" + user_status + '\'' +
                    '}';
        }

        /**
         * user_id : 1
         * user_id_number : 0
         * user_mobile : 13845255539
         * user_sex :
         * user_nickname :
         * user_pic :
         * user_age : 0
         * user_birth : 0000-00-00
         * user_signature :
         * user_school :
         * user_label :
         * user_character :
         * user_qq :
         * user_back_wall :
         * user_inv_code :
         * use_scole : 0
         * user_vip_status : n
         * user_status : y
         */

        private String user_id;
        private String user_id_number;
        private String user_mobile;
        private String user_sex;
        private String user_nickname;
        private String user_pic;
        private String user_age;
        private String user_birth;
        private String user_signature;
        private String user_school;
        private String user_label;
        private String user_character;
        private String user_qq;
        private String user_back_wall;
        private String user_inv_code;
        private String use_scole;
        private String user_vip_status;
        private String user_status;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_id_number() {
            return user_id_number;
        }

        public void setUser_id_number(String user_id_number) {
            this.user_id_number = user_id_number;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(String user_sex) {
            this.user_sex = user_sex;
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

        public String getUser_age() {
            return user_age;
        }

        public void setUser_age(String user_age) {
            this.user_age = user_age;
        }

        public String getUser_birth() {
            return user_birth;
        }

        public void setUser_birth(String user_birth) {
            this.user_birth = user_birth;
        }

        public String getUser_signature() {
            return user_signature;
        }

        public void setUser_signature(String user_signature) {
            this.user_signature = user_signature;
        }

        public String getUser_school() {
            return user_school;
        }

        public void setUser_school(String user_school) {
            this.user_school = user_school;
        }

        public String getUser_label() {
            return user_label;
        }

        public void setUser_label(String user_label) {
            this.user_label = user_label;
        }

        public String getUser_character() {
            return user_character;
        }

        public void setUser_character(String user_character) {
            this.user_character = user_character;
        }

        public String getUser_qq() {
            return user_qq;
        }

        public void setUser_qq(String user_qq) {
            this.user_qq = user_qq;
        }

        public String getUser_back_wall() {
            return user_back_wall;
        }

        public void setUser_back_wall(String user_back_wall) {
            this.user_back_wall = user_back_wall;
        }

        public String getUser_inv_code() {
            return user_inv_code;
        }

        public void setUser_inv_code(String user_inv_code) {
            this.user_inv_code = user_inv_code;
        }

        public String getUse_scole() {
            return use_scole;
        }

        public void setUse_scole(String use_scole) {
            this.use_scole = use_scole;
        }

        public String getUser_vip_status() {
            return user_vip_status;
        }

        public void setUser_vip_status(String user_vip_status) {
            this.user_vip_status = user_vip_status;
        }

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }
    }
}
