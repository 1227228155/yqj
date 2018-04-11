package com.erze.yqj.moudle.comment.reply;

import com.erze.yqj.entity.ReplyBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/8/1.
 */

public interface ReplyContract {
    interface Model extends BaseModel{
        Observable<ReplyBean> getReply(Map<String,String> map);
    }
    interface View extends BaseView{
        void getReplyBean(ReplyBean bean);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getReply(Map<String,String> map);
    }
}
