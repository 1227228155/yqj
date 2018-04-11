package com.erze.yqj.moudle.follow;

import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.entity.UserDataBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/26.
 */

public interface OtherFollowContract {
    interface Model extends BaseModel{
        Observable<UserDataBean> getUserData(Map<String,String> map);
        Observable<CodeBean> getFollowStatus(Map<String,String> map);
    }

    interface View extends BaseView{
        void showErrorLog();
        void getUserData(UserDataBean bean);
        void getFollowStatus(CodeBean bean);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getUserData(Map<String,String> map);
        abstract void getFollowStatus(Map<String,String> map);
    }
}
