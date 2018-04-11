package com.erze.yqj.entity;

/**
 * Created by 1227228155@qq.com on 2017/8/16.
 */

public class UpdateBean {


    /**
     * code : 200
     * result : {"user_id":"34","video_t":"2017-08-16 11:47:54","video_status":"y","video_recommed":"n","video_top":"n","video_watch_times":"0","video_share_times":"0","video_del":"n","description":"as","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/"}
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
                    ", video_t='" + video_t + '\'' +
                    ", video_status='" + video_status + '\'' +
                    ", video_recommed='" + video_recommed + '\'' +
                    ", video_top='" + video_top + '\'' +
                    ", video_watch_times='" + video_watch_times + '\'' +
                    ", video_share_times='" + video_share_times + '\'' +
                    ", video_del='" + video_del + '\'' +
                    ", description='" + description + '\'' +
                    ", video_img='" + video_img + '\'' +
                    ", video_url='" + video_url + '\'' +
                    '}';
        }

        /**
         * user_id : 34
         * video_t : 2017-08-16 11:47:54
         * video_status : y
         * video_recommed : n
         * video_top : n
         * video_watch_times : 0
         * video_share_times : 0
         * video_del : n
         * description : as
         * video_img : https://ks3-cn-beijing.ksyun.com/appimg/
         * video_url : https://ks3-cn-beijing.ksyun.com/appresource/
         */

        private String user_id;
        private String video_t;
        private String video_status;
        private String video_recommed;
        private String video_top;
        private String video_watch_times;
        private String video_share_times;
        private String video_del;
        private String description;
        private String video_img;
        private String video_url;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getVideo_t() {
            return video_t;
        }

        public void setVideo_t(String video_t) {
            this.video_t = video_t;
        }

        public String getVideo_status() {
            return video_status;
        }

        public void setVideo_status(String video_status) {
            this.video_status = video_status;
        }

        public String getVideo_recommed() {
            return video_recommed;
        }

        public void setVideo_recommed(String video_recommed) {
            this.video_recommed = video_recommed;
        }

        public String getVideo_top() {
            return video_top;
        }

        public void setVideo_top(String video_top) {
            this.video_top = video_top;
        }

        public String getVideo_watch_times() {
            return video_watch_times;
        }

        public void setVideo_watch_times(String video_watch_times) {
            this.video_watch_times = video_watch_times;
        }

        public String getVideo_share_times() {
            return video_share_times;
        }

        public void setVideo_share_times(String video_share_times) {
            this.video_share_times = video_share_times;
        }

        public String getVideo_del() {
            return video_del;
        }

        public void setVideo_del(String video_del) {
            this.video_del = video_del;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
    }
}
