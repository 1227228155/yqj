package com.erze.yqj.moudle.user.fansList;

import com.erze.yqj.entity.FansListBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/28.
 */

public interface FansListContract {
    interface Model extends BaseModel{
        Observable<FansListBean> getFans(Map<String,String> map);
    }

    interface View extends BaseView{
            void getFansBean(FansListBean bean);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void  getFansBean(Map<String,String> map);
    }
}
