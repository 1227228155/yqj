package com.erze.yqj.entity;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/7/25.
 */

public class UserDataBean {


    /**
     * code : 200
     * result : {"atten_user_count":"1","user_info":{"user_id":"26","user_status":"y","user_back_wall":"https://yiapp.yiqijun.com/Uploads/UserPic/2017-09-07/59b0b9b6802d2.png","user_vip_status":"n","user_id_number":"841448","user_sex":"男","user_nickname":"Rann","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/tou.png","user_age":"0","user_signature":"","user_label":"文艺,逗比,"},"resource_list":[{"video_id":"66","video_t":"2017-08-17","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/20170817095854.jpg","video_type":"1","video_top":"n","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erzekeji.NYQJ/20170817095854"},{"video_id":"65","video_t":"2017-08-17","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/20170817095844.jpg","video_type":"1","video_top":"n","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erzekeji.NYQJ/20170817095844"}],"atten_status":"n"}
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
         * atten_user_count : 1
         * user_info : {"user_id":"26","user_status":"y","user_back_wall":"https://yiapp.yiqijun.com/Uploads/UserPic/2017-09-07/59b0b9b6802d2.png","user_vip_status":"n","user_id_number":"841448","user_sex":"男","user_nickname":"Rann","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/tou.png","user_age":"0","user_signature":"","user_label":"文艺,逗比,"}
         * resource_list : [{"video_id":"66","video_t":"2017-08-17","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/20170817095854.jpg","video_type":"1","video_top":"n","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erzekeji.NYQJ/20170817095854"},{"video_id":"65","video_t":"2017-08-17","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/20170817095844.jpg","video_type":"1","video_top":"n","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erzekeji.NYQJ/20170817095844"}]
         * atten_status : n
         */

        private String atten_user_count;
        private UserInfoBean user_info;
        private String atten_status;
        private List<ResourceListBean> resource_list;

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

        public String getAtten_status() {
            return atten_status;
        }

        public void setAtten_status(String atten_status) {
            this.atten_status = atten_status;
        }

        public List<ResourceListBean> getResource_list() {
            return resource_list;
        }

        public void setResource_list(List<ResourceListBean> resource_list) {
            this.resource_list = resource_list;
        }

        public static class UserInfoBean {
            /**
             * user_id : 26
             * user_status : y
             * user_back_wall : https://yiapp.yiqijun.com/Uploads/UserPic/2017-09-07/59b0b9b6802d2.png
             * user_vip_status : n
             * user_id_number : 841448
             * user_sex : 男
             * user_nickname : Rann
             * user_pic : https://yiapp.yiqijun.com/Uploads/UserPic/tou.png
             * user_age : 0
             * user_signature :
             * user_label : 文艺,逗比,
             */

            private String user_id;
            private String user_status;
            private String user_back_wall;
            private String user_vip_status;
            private String user_id_number;
            private String user_sex;
            private String user_nickname;
            private String user_pic;
            private String user_age;
            private String user_signature;
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

            public String getUser_signature() {
                return user_signature;
            }

            public void setUser_signature(String user_signature) {
                this.user_signature = user_signature;
            }

            public String getUser_label() {
                return user_label;
            }

            public void setUser_label(String user_label) {
                this.user_label = user_label;
            }
        }

        public static class ResourceListBean {
            /**
             * video_id : 66
             * video_t : 2017-08-17
             * video_img : https://ks3-cn-beijing.ksyun.com/appimg/20170817095854.jpg
             * video_type : 1
             * video_top : n
             * video_description :
             * video_watch_times : 0
             * video_url : https://ks3-cn-beijing.ksyun.com/appresource/com.erzekeji.NYQJ/20170817095854
             */

            private String video_id;
            private String video_t;
            private String video_img;
            private String video_type;
            private String video_top;
            private String video_description;
            private String video_watch_times;
            private String video_url;

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public String getVideo_t() {
                return video_t;
            }

            public void setVideo_t(String video_t) {
                this.video_t = video_t;
            }

            public String getVideo_img() {
                return video_img;
            }

            public void setVideo_img(String video_img) {
                this.video_img = video_img;
            }

            public String getVideo_type() {
                return video_type;
            }

            public void setVideo_type(String video_type) {
                this.video_type = video_type;
            }

            public String getVideo_top() {
                return video_top;
            }

            public void setVideo_top(String video_top) {
                this.video_top = video_top;
            }

            public String getVideo_description() {
                return video_description;
            }

            public void setVideo_description(String video_description) {
                this.video_description = video_description;
            }

            public String getVideo_watch_times() {
                return video_watch_times;
            }

            public void setVideo_watch_times(String video_watch_times) {
                this.video_watch_times = video_watch_times;
            }

            public String getVideo_url() {
                return video_url;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }
        }
    }
}
