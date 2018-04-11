package com.erze.yqj.moudle.follow;

import com.erze.yqj.entity.FollowBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/27.
 */

public interface FollowContract  {
    interface Model extends BaseModel{
        Observable<FollowBean> getFollow(Map<String, String> map);
    }

    interface View extends BaseView{
            void getFollowBean(FollowBean bean);
        void showErrorLog();
        void showEmptyLog();
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getFollow(Map<String,String> map);
    }
}
