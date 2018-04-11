package com.erze.yqj.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/7/31.
 */

public class CommentBean implements Serializable {


    /**
     * code : 200
     * result : {"comm_count":"1","list":[{"comments_id":"79","resources_id":"10","comments_contents":"55qEduWQg+mGiw==","comments_t":"2017-09-05 14:27:43","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/2017-08-28/59a3cd60c3624.jpg","user_nickname":"李","user_id":"41","reply_count":"1","reply_nickname":"李","comm_upvote_num":"0","user_comm_upvote_status":"n"}],"coll":"n","user_video_upvote":"n"}
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

    public static class ResultBean implements Serializable{
        /**
         * comm_count : 1
         * list : [{"comments_id":"79","resources_id":"10","comments_contents":"55qEduWQg+mGiw==","comments_t":"2017-09-05 14:27:43","user_pic":"https://yiapp.yiqijun.com/Uploads/UserPic/2017-08-28/59a3cd60c3624.jpg","user_nickname":"李","user_id":"41","reply_count":"1","reply_nickname":"李","comm_upvote_num":"0","user_comm_upvote_status":"n"}]
         * coll : n
         * user_video_upvote : n
         */

        private String comm_count;
        private String coll;
        private String user_video_upvote;
        private List<ListBean> list;

        public String getComm_count() {
            return comm_count;
        }

        public void setComm_count(String comm_count) {
            this.comm_count = comm_count;
        }

        public String getColl() {
            return coll;
        }

        public void setColl(String coll) {
            this.coll = coll;
        }

        public String getUser_video_upvote() {
            return user_video_upvote;
        }

        public void setUser_video_upvote(String user_video_upvote) {
            this.user_video_upvote = user_video_upvote;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean  implements Serializable{
            /**
             * comments_id : 79
             * resources_id : 10
             * comments_contents : 55qEduWQg+mGiw==
             * comments_t : 2017-09-05 14:27:43
             * user_pic : https://yiapp.yiqijun.com/Uploads/UserPic/2017-08-28/59a3cd60c3624.jpg
             * user_nickname : 李
             * user_id : 41
             * reply_count : 1
             * reply_nickname : 李
             * comm_upvote_num : 0
             * user_comm_upvote_status : n
             */

            private String comments_id;
            private String resources_id;
            private String comments_contents;
            private String comments_t;
            private String user_pic;
            private String user_nickname;
            private String user_id;
            private String reply_count;
            private String reply_nickname;
            private String comm_upvote_num;
            private String user_comm_upvote_status;

            public String getComments_id() {
                return comments_id;
            }

            public void setComments_id(String comments_id) {
                this.comments_id = comments_id;
            }

            public String getResources_id() {
                return resources_id;
            }

            public void setResources_id(String resources_id) {
                this.resources_id = resources_id;
            }

            public String getComments_contents() {
                return comments_contents;
            }

            public void setComments_contents(String comments_contents) {
                this.comments_contents = comments_contents;
            }

            public String getComments_t() {
                return comments_t;
            }

            public void setComments_t(String comments_t) {
                this.comments_t = comments_t;
            }

            public String getUser_pic() {
                return user_pic;
            }

            public void setUser_pic(String user_pic) {
                this.user_pic = user_pic;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getReply_count() {
                return reply_count;
            }

            public void setReply_count(String reply_count) {
                this.reply_count = reply_count;
            }

            public String getReply_nickname() {
                return reply_nickname;
            }

            public void setReply_nickname(String reply_nickname) {
                this.reply_nickname = reply_nickname;
            }

            public String getComm_upvote_num() {
                return comm_upvote_num;
            }

            public void setComm_upvote_num(String comm_upvote_num) {
                this.comm_upvote_num = comm_upvote_num;
            }

            public String getUser_comm_upvote_status() {
                return user_comm_upvote_status;
            }

            public void setUser_comm_upvote_status(String user_comm_upvote_status) {
                this.user_comm_upvote_status = user_comm_upvote_status;
            }
        }
    }
}
