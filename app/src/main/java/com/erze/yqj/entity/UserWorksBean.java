package com.erze.yqj.entity;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/9/7.
 */

public class UserWorksBean {

    /**
     * code : 200
     * result : [{"video_id":"82","video_t":"2017-09-07","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/com.erze.yqj/1504748722011","video_type":"1","video_top":"n","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/appaudio/com.erze.yqj/1504748706782"},{"video_id":"79","video_t":"2017-09-06","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/com.erze.yqj/1504686733445","video_type":"1","video_top":"n","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/appaudio/com.erze.yqj/1504686733445"},{"video_id":"78","video_t":"2017-09-06","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/com.erze.yqj/1504686298782","video_type":"1","video_top":"n","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/appaudio/com.erze.yqj/1504686298782"},{"video_id":"69","video_t":"2017-08-18","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/com.erze.yqj/1503017086207","video_type":"1","video_top":"n","video_description":"","video_watch_times":"7","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/1503017086207"},{"video_id":"68","video_t":"2017-08-18","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/com.erze.yqj/1503016945369","video_type":"1","video_top":"n","video_description":"","video_watch_times":"1","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/1503016945369"},{"video_id":"67","video_t":"2017-08-17","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/com.erze.yqj/1502964311235","video_type":"1","video_top":"n","video_description":"","video_watch_times":"0","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/1502964311235"}]
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
         * video_id : 82
         * video_t : 2017-09-07
         * video_img : https://ks3-cn-beijing.ksyun.com/appimg/com.erze.yqj/1504748722011
         * video_type : 1
         * video_top : n
         * video_description :
         * video_watch_times : 0
         * video_url : https://ks3-cn-beijing.ksyun.com/appaudio/com.erze.yqj/1504748706782
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
