package com.erze.yqj.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/7/25.
 */

public class WorldBean implements Serializable{


    /**
     * code : 200
     * result : {"banner":[{"banner_img":"https://yiapp.yiqijun.com/Uploads/Banner/2017-08-16/5993a8c8450d6.png","banner_url":""},{"banner_img":"https://yiapp.yiqijun.com/Uploads/Banner/2017-08-16/5993a8c129fab.png","banner_url":""},{"banner_img":"https://yiapp.yiqijun.com/Uploads/Banner/2017-08-16/5993a8baae0c8.png","banner_url":""}],"anno":[{"anno_title":"义气君权限改版","anno_id":"5"},{"anno_title":"4公告","anno_id":"4"},{"anno_title":"3公告","anno_id":"3"}],"video_list":[{"user_id":"5","user_nickname":"用户5的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/539e14c334bc401e468894ad0206aaf9395e3bad8e0c-xtg1Te_fw658.jpg","user_label":"用户5的标签","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/10.png","video_id":"10","video_type":"1","video_description":"","video_watch_times":"126","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA0MDgyMjIzMTVfMjQyOTYwMTgzXzE5NDg4OTkwMTZfMV8z.mp4","video_t":"2017-08-01"},{"user_id":"1","user_nickname":"用户1的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/tou.png","user_label":"调皮,扯淡,开心","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/WLA7PVOT63H9R%25U%7D73R%24H0U.png","video_id":"1","video_type":"1","video_description":"","video_watch_times":"108","video_url":"appresource/com.erze.yqj/BMjAxNTExMTQxNTMwMzJfODMxODE2MV80NTUxNzQ0NTNfMV8z.mp4","video_t":"2017-06-01"},{"user_id":"6","user_nickname":"用户6名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/cd61f13257b8ffc99d3f0397e20a216a9243f3d413bff-BddhLH_fw658.jpg","user_label":"用户6签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/11.png","video_id":"11","video_type":"1","video_description":"","video_watch_times":"236","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNzA0MjEyMjI5MjJfNDAxMTk3ODU2XzIwMjUyMTUyNTZfMV8z_hd.mp4","video_t":"0000-00-00"},{"user_id":"2","user_nickname":"用户2的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/5c7d610aed07f17c54e15ade5e976de7328d7b2e24d17-zd0OCr_fw658.jpg","user_label":"用户2签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/8.png","video_id":"8","video_type":"3","video_description":"","video_watch_times":"12","video_url":"appresource/com.erze.yqj/BMjAxNzA0MDMxNTQzMTZfMTczMzQzNDJfMTkxNDU1OTE1OV8xXzM%3D.mp4","video_t":"2017-07-17"},{"user_id":"3","user_nickname":"用户3的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/062e0c0be6e092440182549bdee3000b662bf8bd2668c-UT9r8N_fw658.jpg","user_label":"用户3签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/2156.png","video_id":"4","video_type":"3","video_description":"","video_watch_times":"32","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNjEyMjgxNjIxMjZfODk4NzgwMTZfMTQyNzkwNDQyMV8yXzM%3D.mp4","video_t":"2017-06-10"},{"user_id":"2","user_nickname":"用户2的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/5c7d610aed07f17c54e15ade5e976de7328d7b2e24d17-zd0OCr_fw658.jpg","user_label":"用户2签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/3.png","video_id":"3","video_type":"1","video_description":"用户3发表资源","video_watch_times":"5","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNjExMTUwOTI3NDhfODk4NzgwMTZfMTI1Nzc4MTgwNV8yXzM%3D.mp4","video_t":"2017-06-09"},{"user_id":"3","user_nickname":"用户3的名字","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/062e0c0be6e092440182549bdee3000b662bf8bd2668c-UT9r8N_fw658.jpg","user_label":"用户3签名","video_img":"https://ks3-cn-beijing.ksyun.com/appimg/20170816091442.png","video_id":"2","video_type":"1","video_description":"","video_watch_times":"31","video_url":"https://ks3-cn-beijing.ksyun.com/appresource/com.erze.yqj/BMjAxNjEwMjcyMjQwMTdfMTEzNjk2NzE1XzExOTQyODY4ODBfMl8z.mp4","video_t":"2017-06-08"}]}
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
        private List<BannerBean> banner;
        private List<AnnoBean> anno;
        private List<VideoListBean> video_list;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<AnnoBean> getAnno() {
            return anno;
        }

        public void setAnno(List<AnnoBean> anno) {
            this.anno = anno;
        }

        public List<VideoListBean> getVideo_list() {
            return video_list;
        }

        public void setVideo_list(List<VideoListBean> video_list) {
            this.video_list = video_list;
        }

        public static class BannerBean {
            /**
             * banner_img : https://yiapp.yiqijun.com/Uploads/Banner/2017-08-16/5993a8c8450d6.png
             * banner_url :
             */

            private String banner_img;
            private String banner_url;

            public String getBanner_img() {
                return banner_img;
            }

            public void setBanner_img(String banner_img) {
                this.banner_img = banner_img;
            }

            public String getBanner_url() {
                return banner_url;
            }

            public void setBanner_url(String banner_url) {
                this.banner_url = banner_url;
            }
        }

        public static class AnnoBean {
            /**
             * anno_title : 义气君权限改版
             * anno_id : 5
             */

            private String anno_title;
            private String anno_id;

            public String getAnno_title() {
                return anno_title;
            }

            public void setAnno_title(String anno_title) {
                this.anno_title = anno_title;
            }

            public String getAnno_id() {
                return anno_id;
            }

            public void setAnno_id(String anno_id) {
                this.anno_id = anno_id;
            }
        }

        public static class VideoListBean  implements Serializable{
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
}
