package com.erze.yqj.moudle.comment;

import com.erze.yqj.entity.CommentBean;
import com.erze.yqj.entity.SendComment;
import com.erze.yqj.entity.SendReply;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/31.
 */

public interface CommentContract {
    interface Model extends BaseModel{
        Observable<CommentBean> getComment(Map<String, String> map);
        Observable<SendComment> sendComment(Map<String, String> map);
        Observable<SendReply> sendReply(Map<String, String> map);
    }

    interface View extends BaseView{
        void getCommentBean(CommentBean bean);
        void sendComment(SendComment sendComment);
        void sendReply(SendReply sendComment);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getComment(Map<String, String> map);
        abstract void sendComment(Map<String, String> map);
        abstract void sendReply(Map<String, String> map);
    }
}
