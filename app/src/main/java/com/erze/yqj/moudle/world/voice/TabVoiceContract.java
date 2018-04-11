package com.erze.yqj.moudle.world.voice;

import com.erze.yqj.entity.Videobean;
import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/8/3.
 */

public interface TabVoiceContract {
    interface Model extends BaseModel{
        Observable<Videobean> getVideo(Map<String,String> map);
    }

    interface View extends BaseView{
        void showErrorLog();
        void getVideo(Videobean videobean);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getVideo(Map<String,String> map);
    }
}
