package com.erze.yqj.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/7/26.
 */

public class Videobean implements Serializable {


    /**
     * code : 200
     * result : [{"user_id":"5","user_nickname":"用户5的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/539e14c334bc401e468894ad0206aaf9395e3bad8e0c-xtg1Te_fw658.jpg","user_label":"用户5的标签","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/10.png","video_id":"10","video_type":"1","video_description":"","video_watch_times":"126","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA0MDgyMjIzMTVfMjQyOTYwMTgzXzE5NDg4OTkwMTZfMV8z.mp4","video_t":"2017-08-01"},{"user_id":"1","user_nickname":"用户1的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/tou.png","user_label":"调皮,扯淡,开心","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/WLA7PVOT63H9R%25U%7D73R%24H0U.png","video_id":"1","video_type":"1","video_description":"","video_watch_times":"108","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNTExMTQxNTMwMzJfODMxODE2MV80NTUxNzQ0NTNfMV8z.mp4","video_t":"2017-06-01"},{"user_id":"2","user_nickname":"用户2的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/5c7d610aed07f17c54e15ade5e976de7328d7b2e24d17-zd0OCr_fw658.jpg","user_label":"用户2签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/19.png","video_id":"19","video_type":"1","video_description":"","video_watch_times":"555","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzAyMTIxMTEwNDNfNzY1NzcwMl8xNjUwNTA1ODI4XzFfMw%3D%3D.mp4","video_t":"2017-08-04"},{"user_id":"5","user_nickname":"用户5的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/539e14c334bc401e468894ad0206aaf9395e3bad8e0c-xtg1Te_fw658.jpg","user_label":"用户5的标签","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/18.png","video_id":"18","video_type":"","video_description":"","video_watch_times":"102","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA2MTYxNzQ3NTBfMzgzODk5OTA5XzI0MDg4NTE3ODNfMl8z_hd.mp4","video_t":"2017-08-03"},{"user_id":"4","user_nickname":"用户4的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/afda17e8f2e03a372c8fc0b877a84d96d86f95b5d134-jGGLwZ_fw658.jpg","user_label":"用户四的签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/17.png","video_id":"17","video_type":"","video_description":"","video_watch_times":"1000","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA2MTYxMTA2MDBfNDUyNzc4NTdfMjQwNjM0NTI5MF8yXzM%3D.mp4","video_t":"2017-08-02"},{"user_id":"3","user_nickname":"用户3的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/062e0c0be6e092440182549bdee3000b662bf8bd2668c-UT9r8N_fw658.jpg","user_label":"用户3签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/16.png","video_id":"16","video_type":"1","video_description":"","video_watch_times":"569","video_url":"\r\nhttps://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA2MTMxOTAyMzVfMjEyNTUzNjFfMjM4OTAyNTY2Nl8xXzM%3D_hd.mp4","video_t":"2017-08-05"},{"user_id":"2","user_nickname":"用户2的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/5c7d610aed07f17c54e15ade5e976de7328d7b2e24d17-zd0OCr_fw658.jpg","user_label":"用户2签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/15.png","video_id":"15","video_type":"1","video_description":"","video_watch_times":"100","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA2MTAxODQ1MjhfMjI5MjMzMzg2XzIzNjYyNzg3NDJfMV8z_hd.mp4","video_t":"2017-08-03"},{"user_id":"1","user_nickname":"用户1的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/tou.png","user_label":"调皮,扯淡,开心","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/14.png","video_id":"14","video_type":"","video_description":"","video_watch_times":"592","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA1MjQxMjU2MzJfNjgxNjc4OF8yMjQxMDY2MDE3XzFfMw%3D%3D_b.mp4","video_t":"2017-08-02"},{"user_id":"6","user_nickname":"用户6名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/cd61f13257b8ffc99d3f0397e20a216a9243f3d413bff-BddhLH_fw658.jpg","user_label":"用户6签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/13.png","video_id":"13","video_type":"1","video_description":"","video_watch_times":"563","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA0MjcxNzMwMTNfNDAxMTk3ODU2XzIwNTk1MjA4NzZfMV8z.mp4","video_t":"2017-08-01"},{"user_id":"2","user_nickname":"用户2的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/5c7d610aed07f17c54e15ade5e976de7328d7b2e24d17-zd0OCr_fw658.jpg","user_label":"用户2签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/12.png","video_id":"12","video_type":"1","video_description":"","video_watch_times":"569","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA0MjYxMTIxMDZfNTY5MDQyMV8yMDUyMjg0Nzc5XzFfMw%3D%3D_hd.mp4","video_t":"2017-08-02"}]
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
         * user_id : 5
         * user_nickname : 用户5的名字
         * user_pic : https://yiapp.yiqijun.com/Uploads/UserPic/539e14c334bc401e468894ad0206aaf9395e3bad8e0c-xtg1Te_fw658.jpg
         * user_label : 用户5的标签
         * video_img : https://ks3-cn-beijing.ksyun.com/appimg/10.png
         * video_id : 10
         * video_type : 1
         * video_description :
         * video_watch_times : 126
         * video_url : https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA0MDgyMjIzMTVfMjQyOTYwMTgzXzE5NDg4OTkwMTZfMV8z.mp4
         * video_t : 2017-08-01
         */

        private String user_id;
        private String user_nickname;
        private String user_pic;
        private String user_label;
        private String video_img;
        private String video_id;
        private String video_type;
        private String video_description;
        private String video_watch_times;
        private String video_url;
        private String video_t;

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

        public String getUser_label() {
            return user_label;
        }

        public void setUser_label(String user_label) {
            this.user_label = user_label;
        }

        public String getVideo_img() {
            return video_img;
        }

        public void setVideo_img(String video_img) {
            this.video_img = video_img;
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

        public String getVideo_t() {
            return video_t;
        }

        public void setVideo_t(String video_t) {
            this.video_t = video_t;
        }
    }
}
