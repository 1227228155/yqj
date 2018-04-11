package com.erze.yqj.entity;

/**
 * Created by 1227228155@qq.com on 2017/7/27.
 */

public class UserBean {


    /**
     * code : 200
     * result : {"video_count":"4","atten_count":"4","atten_user_count":"7","user_info":{"user_id":"1","user_status":"y","user_character":"涩会","user_qq":"1185802277","user_back_wall":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504671790191&di=dfddede551bf862ffd6fff5666e93e80&imgtype=0&src=http%3A%2F%2Fs11.sinaimg.cn%2Fmiddle%2F4a30d769h8e17c19d6e2a%26690","user_vip_status":"y","user_id_number":"1111111","user_mobile":"18811127855","user_sex":"男","user_nickname":"用户1的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/tou.png","user_age":"18","user_birth":"2017-07-01","user_signature":"44G654Gs5Zug5Li65Lyx4pWv77yM6I6q5Zac5qyi5LiK5LqG6JaE6I235pyo57OW6YaH77yCY29sZO+5jwoKCuOBuueBrOWboOS4uuS8seKVr++8jOiOquWWnOasouS4iuS6huiWhOiNt+acqOezlumGh++8gmNvbGTvuY8=\r\n\r\n\r\n","user_school":"你猜呢学校","user_label":"调皮,扯淡,开心"}}
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
         * video_count : 4
         * atten_count : 4
         * atten_user_count : 7
         * user_info : {"user_id":"1","user_status":"y","user_character":"涩会","user_qq":"1185802277","user_back_wall":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504671790191&di=dfddede551bf862ffd6fff5666e93e80&imgtype=0&src=http%3A%2F%2Fs11.sinaimg.cn%2Fmiddle%2F4a30d769h8e17c19d6e2a%26690","user_vip_status":"y","user_id_number":"1111111","user_mobile":"18811127855","user_sex":"男","user_nickname":"用户1的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/tou.png","user_age":"18","user_birth":"2017-07-01","user_signature":"44G654Gs5Zug5Li65Lyx4pWv77yM6I6q5Zac5qyi5LiK5LqG6JaE6I235pyo57OW6YaH77yCY29sZO+5jwoKCuOBuueBrOWboOS4uuS8seKVr++8jOiOquWWnOasouS4iuS6huiWhOiNt+acqOezlumGh++8gmNvbGTvuY8=\r\n\r\n\r\n","user_school":"你猜呢学校","user_label":"调皮,扯淡,开心"}
         */

        private String video_count;
        private String atten_count;
        private String atten_user_count;
        private UserInfoBean user_info;

        public String getVideo_count() {
            return video_count;
        }

        public void setVideo_count(String video_count) {
            this.video_count = video_count;
        }

        public String getAtten_count() {
            return atten_count;
        }

        public void setAtten_count(String atten_count) {
            this.atten_count = atten_count;
        }

        public String getAtten_user_count() {
            return atten_user_count;
        }

        public void setAtten_user_count(String atten_user_count) {
            this.atten_user_count = atten_user_count;
        }

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public static class UserInfoBean {
            /**
             * user_id : 1
             * user_status : y
             * user_character : 涩会
             * user_qq : 1185802277
             * user_back_wall : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504671790191&di=dfddede551bf862ffd6fff5666e93e80&imgtype=0&src=http%3A%2F%2Fs11.sinaimg.cn%2Fmiddle%2F4a30d769h8e17c19d6e2a%26690
             * user_vip_status : y
             * user_id_number : 1111111
             * user_mobile : 18811127855
             * user_sex : 男
             * user_nickname : 用户1的名字
             * user_pic : https://yiapp.yiqijun.com/Uploads/UserPic/tou.png
             * user_age : 18
             * user_birth : 2017-07-01
             * user_signature : 44G654Gs5Zug5Li65Lyx4pWv77yM6I6q5Zac5qyi5LiK5LqG6JaE6I235pyo57OW6YaH77yCY29sZO+5jwoKCuOBuueBrOWboOS4uuS8seKVr++8jOiOquWWnOasouS4iuS6huiWhOiNt+acqOezlumGh++8gmNvbGTvuY8=



             * user_school : 你猜呢学校
             * user_label : 调皮,扯淡,开心
             */

            private String user_id;
            private String user_status;
            private String user_character;
            private String user_qq;
            private String user_back_wall;
            private String user_vip_status;
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

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_status() {
                return user_status;
            }

            public void setUser_status(String user_status) {
                this.user_status = user_status;
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

            public String getUser_vip_status() {
                return user_vip_status;
            }

            public void setUser_vip_status(String user_vip_status) {
                this.user_vip_status = user_vip_status;
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
        }
    }
}
