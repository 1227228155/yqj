package com.erze.yqj.moudle.voice;

import com.erze.yqj.rxframe.base.BaseModel;
import com.erze.yqj.rxframe.base.BasePresenter;
import com.erze.yqj.rxframe.base.BaseView;

/**
 * Created by 1227228155@qq.com on 2017/7/14.
 */

public interface VoiceContract {
    interface Model extends BaseModel{
    }

    interface View extends BaseView{
    }

    abstract class Presenter extends BasePresenter<Model,View>{
    }
}
