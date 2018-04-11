package com.erze.yqj.moudle.user.collection;

import com.erze.yqj.entity.FollowBean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/28.
 */

public interface CollectionContract {
    interface Model extends BaseModel{
        Observable<FollowBean> getCollectionInfo(Map<String,String> map);
    }

    interface View extends BaseView{
        void  getInfo(FollowBean bean);
        void  showEmptyLog();
    }

    abstract class Presenter extends BasePresenter<Model,View>{
       abstract void getCollectionInfo(Map<String,String> map);
    }
}
