package com.erze.yqj.moudle.user.followlist;

import com.erze.yqj.entity.FansListBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/27.
 */

public interface FollowListContract {
    interface Model extends BaseModel{
        Observable<FansListBean> getFollowList(Map<String,String> map);
    }
    interface View extends BaseView{
        void getFollowList(FansListBean bean);
        void showErrorLog();

    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getFollowList(Map<String,String> map);
    }
}
