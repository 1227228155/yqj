package com.erze.yqj.entity;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/7/27.
 */

public class FollowBean {


    /**
     * code : 200
     * result : [{"video_comment_num":"0","video_upvote_num":"0","video_img":"www.baidu.com","user_id":"1","user_id_number":"1111111","user_nickname":"用户1的名字","user_pic":"http://www.ezh5.com/1.img","user_vip_status":"y","video_id":"9","video_type":"1","video_t":"2017-07-28","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/ceshi/a","upvote_status":"n"},{"video_comment_num":"0","video_upvote_num":"0","video_img":"","user_id":"1","user_id_number":"1111111","user_nickname":"用户1的名字","user_pic":"http://www.ezh5.com/1.img","user_vip_status":"y","video_id":"7","video_type":"3","video_t":"2017-07-17","video_description":"","video_watch_times":"0","video_url":"apper/12454115","upvote_status":"n"},{"video_comment_num":"0","video_upvote_num":"0","video_img":"","user_id":"2","user_id_number":"22222","user_nickname":"用户2的名字","user_pic":"用户2的头像","user_vip_status":"n","video_id":"8","video_type":"3","video_t":"2017-07-17","video_description":"","video_watch_times":"0","video_url":"/appimg/apper/12454115_/appimg/aaper/456322","upvote_status":"n"},{"video_comment_num":"0","video_upvote_num":"0","video_img":"","user_id":"5","user_id_number":"55555","user_nickname":"用户5的名字","user_pic":"用户5的头像","user_vip_status":"n","video_id":"5","video_type":"3","video_t":"2017-06-29","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/mp3/","upvote_status":"n"},{"video_comment_num":"0","video_upvote_num":"0","video_img":"http://img5.duitang.com/uploads/item/201409/02/20140902032259_j5xjQ.gif","user_id":"2","user_id_number":"22222","user_nickname":"用户2的名字","user_pic":"用户2的头像","user_vip_status":"n","video_id":"3","video_type":"3","video_t":"2017-06-09","video_description":"用户3发表资源","video_watch_times":"2","video_url":"/aaaa/ddddd","upvote_status":"n"},{"video_comment_num":"0","video_upvote_num":"0","video_img":"http://img02.tooopen.com/images/20150504/tooopen_sy_121621637679.jpg","user_id":"4","user_id_number":"44444444","user_nickname":"用户4的名字","user_pic":"用户4的图片","user_vip_status":"n","video_id":"6","video_type":"3","video_t":"2017-06-05","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/mp3/","upvote_status":"n"},{"video_comment_num":"0","video_upvote_num":"0","video_img":"","user_id":"1","user_id_number":"1111111","user_nickname":"用户1的名字","user_pic":"http://www.ezh5.com/1.img","user_vip_status":"y","video_id":"1","video_type":"3","video_t":"2017-06-01","video_description":"用户1发表的资源测试","video_watch_times":"102","video_url":"/aaa/bbbb","upvote_status":"y"}]
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
         * video_comment_num : 0
         * video_upvote_num : 0
         * video_img : www.baidu.com
         * user_id : 1
         * user_id_number : 1111111
         * user_nickname : 用户1的名字
         * user_pic : http://www.ezh5.com/1.img
         * user_vip_status : y
         * video_id : 9
         * video_type : 1
         * video_t : 2017-07-28
         * video_description :
         * video_watch_times : 0
         * video_url : https://ks3-cn-beijing.ksyun.com/appresource/ceshi/a
         * upvote_status : n
         */

        private String video_comment_num;
        private String video_upvote_num;
        private String video_img;
        private String user_id;
        private String user_id_number;
        private String user_nickname;
        private String user_pic;
        private String user_vip_status;
        private String video_id;
        private String video_type;
        private String video_t;
        private String video_description;
        private String video_watch_times;
        private String video_url;
        private String upvote_status;

        public String getVideo_comment_num() {
            return video_comment_num;
        }

        public void setVideo_comment_num(String video_comment_num) {
            this.video_comment_num = video_comment_num;
        }

        public String getVideo_upvote_num() {
            return video_upvote_num;
        }

        public void setVideo_upvote_num(String video_upvote_num) {
            this.video_upvote_num = video_upvote_num;
        }

        public String getVideo_img() {
            return video_img;
        }

        public void setVideo_img(String video_img) {
            this.video_img = video_img;
        }

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

        public String getUser_vip_status() {
            return user_vip_status;
        }

        public void setUser_vip_status(String user_vip_status) {
            this.user_vip_status = user_vip_status;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getVideo_type() {
            return video_type;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public String getVideo_t() {
            return video_t;
        }

        public void setVideo_t(String video_t) {
            this.video_t = video_t;
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

        public String getUpvote_status() {
            return upvote_status;
        }

        public void setUpvote_status(String upvote_status) {
            this.upvote_status = upvote_status;
        }
    }
}
