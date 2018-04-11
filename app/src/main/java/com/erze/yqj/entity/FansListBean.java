package com.erze.yqj.entity;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/7/26.
 */

public class FansListBean {


    /**
     * code : 200
     * result : {"list":[{"user_id":"2","user_nickname":"用户2的名字","user_pic":"用户2的头像","user_sex":"","user_age":"0","atten_status":"n"},{"user_id":"1","user_nickname":"用户1的名字","user_pic":"http://www.ezh5.com/1.img","user_sex":"男","user_age":"18","atten_status":"y"},{"user_id":"3","user_nickname":"用户3的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/2017-06-26/59506a1dc598d.jpg","user_sex":"女","user_age":"0","atten_status":"n"},{"user_id":"4","user_nickname":"用户4的名字","user_pic":"用户4的图片","user_sex":"","user_age":"0","atten_status":"n"},{"user_id":"5","user_nickname":"用户5的名字","user_pic":"用户5的头像","user_sex":"","user_age":"0","atten_status":"n"}],"count":"5"}
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
         * list : [{"user_id":"2","user_nickname":"用户2的名字","user_pic":"用户2的头像","user_sex":"","user_age":"0","atten_status":"n"},{"user_id":"1","user_nickname":"用户1的名字","user_pic":"http://www.ezh5.com/1.img","user_sex":"男","user_age":"18","atten_status":"y"},{"user_id":"3","user_nickname":"用户3的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/2017-06-26/59506a1dc598d.jpg","user_sex":"女","user_age":"0","atten_status":"n"},{"user_id":"4","user_nickname":"用户4的名字","user_pic":"用户4的图片","user_sex":"","user_age":"0","atten_status":"n"},{"user_id":"5","user_nickname":"用户5的名字","user_pic":"用户5的头像","user_sex":"","user_age":"0","atten_status":"n"}]
         * count : 5
         */

        private String count;
        private List<ListBean> list;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * user_id : 2
             * user_nickname : 用户2的名字
             * user_pic : 用户2的头像
             * user_sex :
             * user_age : 0
             * atten_status : n
             */

            private String user_id;
            private String user_nickname;
            private String user_pic;
            private String user_sex;
            private String user_age;
            private String atten_status;

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

            public String getUser_sex() {
                return user_sex;
            }

            public void setUser_sex(String user_sex) {
                this.user_sex = user_sex;
            }

            public String getUser_age() {
                return user_age;
            }

            public void setUser_age(String user_age) {
                this.user_age = user_age;
            }

            public String getAtten_status() {
                return atten_status;
            }

            public void setAtten_status(String atten_status) {
                this.atten_status = atten_status;
            }
        }
    }
}
