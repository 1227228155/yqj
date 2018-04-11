package com.erze.yqj.moudle.world;

import com.erze.yqj.entity.WorldBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/25.
 */

public interface WorldContract {
    interface View extends BaseView{
        void showErrorLog();
        void showErrorNet();
        void  getRecommendData(WorldBean worldBean);
        void  showData();
    }

    interface Model extends BaseModel{
        Observable<WorldBean> getRecommendData(Map<String,String> map);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getRecommendData(Map<String,String> map);
    }

}
