package com.erze.yqj.moudle.main;

import com.erze.yqj.entity.KSYSDKBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/6.
 */

public interface MainContract {
    interface Model extends BaseModel{
        Observable<KSYSDKBean> getSDK(String pkg);
    }

    interface View extends BaseView{
        void getSDK(KSYSDKBean ksysdkBean);
        void  gotoRecord();
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getSDK(String pkg);
    }
}
