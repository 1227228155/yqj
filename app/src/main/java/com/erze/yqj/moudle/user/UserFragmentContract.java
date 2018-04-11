package com.erze.yqj.moudle.user;

import com.erze.yqj.entity.UserBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/25.
 */

public interface UserFragmentContract {
    interface Model extends BaseModel{
        Observable<UserBean> getUserData(Map<String,String> map);
    }

    interface View extends BaseView{
        void showErrorLog();
        void getUserDataBean(UserBean bean);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getUserData(Map<String,String> map);
    }
}
