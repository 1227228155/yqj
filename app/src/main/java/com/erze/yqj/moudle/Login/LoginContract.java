package com.erze.yqj.moudle.Login;

import com.erze.yqj.entity.RegisterBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/6/21.
 */

public interface LoginContract {
    interface Model extends BaseModel{
        Observable<RegisterBean> getRegisterUser(String userPhone);
    }

    interface View extends BaseView{
        void showUserPhone(RegisterBean registerBean);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void RegisterUser(String userPhone);
    }
}
