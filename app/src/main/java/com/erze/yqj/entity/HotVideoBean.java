package com.erze.yqj.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/9/7.
 */

public class HotVideoBean {

    /**
     * code : 200
     * result : [{"user_id":"3","user_nickname":"用户3的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/062e0c0be6e092440182549bdee3000b662bf8bd2668c-UT9r8N_fw658.jpg","video_id":"2","video_type":"1","video_watch_times":"34","video_upvote_num":"50","video_t":"2017-06-08","video_comment_num":"80","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/20170816091442.png","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNjEwMjcyMjQwMTdfMTEzNjk2NzE1XzExOTQyODY4ODBfMl8z.mp4","user_atten_status":"y","upvote_status":"n"},{"user_id":"2","user_nickname":"用户2的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/5c7d610aed07f17c54e15ade5e976de7328d7b2e24d17-zd0OCr_fw658.jpg","video_id":"3","video_type":"1","video_watch_times":"6","video_upvote_num":"15","video_t":"2017-06-09","video_comment_num":"22","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/3.png","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNjExMTUwOTI3NDhfODk4NzgwMTZfMTI1Nzc4MTgwNV8yXzM%3D.mp4","user_atten_status":"y","upvote_status":"n"}]
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

    public static class ResultBean implements Serializable {
        /**
         * user_id : 3
         * user_nickname : 用户3的名字
         * user_pic : https://yiapp.yiqijun.com/Uploads/UserPic/062e0c0be6e092440182549bdee3000b662bf8bd2668c-UT9r8N_fw658.jpg
         * video_id : 2
         * video_type : 1
         * video_watch_times : 34
         * video_upvote_num : 50
         * video_t : 2017-06-08
         * video_comment_num : 80
         * video_img : https://ks3-cn-beijing.ksyun.com/appimg/20170816091442.png
         * video_url : https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNjEwMjcyMjQwMTdfMTEzNjk2NzE1XzExOTQyODY4ODBfMl8z.mp4
         * user_atten_status : y
         * upvote_status : n
         */

        private String user_id;
        private String user_nickname;
        private String user_pic;
        private String video_id;
        private String video_type;
        private String video_watch_times;
        private String video_upvote_num;
        private String video_t;
        private String video_comment_num;
        private String video_img;
        private String video_url;
        private String user_atten_status;
        private String upvote_status;

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

        public String getVideo_watch_times() {
            return video_watch_times;
        }

        public void setVideo_watch_times(String video_watch_times) {
            this.video_watch_times = video_watch_times;
        }

        public String getVideo_upvote_num() {
            return video_upvote_num;
        }

        public void setVideo_upvote_num(String video_upvote_num) {
            this.video_upvote_num = video_upvote_num;
        }

        public String getVideo_t() {
            return video_t;
        }

        public void setVideo_t(String video_t) {
            this.video_t = video_t;
        }

        public String getVideo_comment_num() {
            return video_comment_num;
        }

        public void setVideo_comment_num(String video_comment_num) {
            this.video_comment_num = video_comment_num;
        }

        public String getVideo_img() {
            return video_img;
        }

        public void setVideo_img(String video_img) {
            this.video_img = video_img;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getUser_atten_status() {
            return user_atten_status;
        }

        public void setUser_atten_status(String user_atten_status) {
            this.user_atten_status = user_atten_status;
        }

        public String getUpvote_status() {
            return upvote_status;
        }

        public void setUpvote_status(String upvote_status) {
            this.upvote_status = upvote_status;
        }
    }
}
